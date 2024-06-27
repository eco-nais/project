package rs.ac.uns.acs.nais.TimeseriesDatabaseService.configuration;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.exceptions.InfluxException;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.influxdb.client.DeleteApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import rs.ac.uns.acs.nais.TimeseriesDatabaseService.model.FixedExpenses;


@Component
public class InfluxDBConnectionClass {

    @Value("${spring.influx.token}")
    private String token;

    @Value("${spring.influx.bucket}")
    private String bucket;

    @Value("${spring.influx.org}")
    private String org;

    @Value("${spring.influx.url}")
    private String url;

    public InfluxDBClient buildConnection() {
        setToken(token);
        setBucket(bucket);
        setOrg(org);
        setUrl(url);
        return InfluxDBClientFactory.create(getUrl(), getToken().toCharArray(), getOrg(), getBucket());
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // FixedExpenses
    public boolean saveFixedExpense(InfluxDBClient influxDBClient, FixedExpenses fixedExpense) {
        boolean flag = false;
        try {
            WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();
            fixedExpense.setCreated(Instant.now()); // Set current time as createdOn timestamp

            Point point = Point.measurement("fixed_expenses")
                    .addTag("creator_id", fixedExpense.getCreator_id())
                    .addTag("employee", fixedExpense.getEmployee())
                    .addTag("start_date", fixedExpense.getStart_date())
                    .addTag("end_date", fixedExpense.getEnd_date())
                    .addTag("description", fixedExpense.getDescription())
                    .addField(fixedExpense.get_field(), (double)fixedExpense.get_value())
                    .time(fixedExpense.getCreated(), WritePrecision.MS);

            writeApi.writePoint(point);
            System.out.println("Saved new fixed expense!");
            System.out.println("Fixed expense details: [" +
                    "Value: " + fixedExpense.get_value() + " , " +
                    "Field: " + fixedExpense.get_field()  + " , " +
                    "Employee: " + fixedExpense.getEmployee() + " , " +
                    "Start Date: " + fixedExpense.getStart_date() + " , " +
                    "End Date: " + fixedExpense.getEnd_date() + " , " +
                    "Description: " + fixedExpense.getDescription() + "]");
            flag = true;
        } catch (InfluxException e) {
            System.out.println("Exception while saving fixed expense: " + e.getMessage());
        }
        return flag;
    }

    // delete FixedExpenses that have given creator_id, and that are in +-1 seconds period of given time
    public boolean deleteFixedExpenses(InfluxDBClient influxDBClient, String creatorId, Instant time) {
        boolean flag = false;
        DeleteApi deleteApi = influxDBClient.getDeleteApi();

        try {
            OffsetDateTime start = OffsetDateTime.ofInstant(time, OffsetDateTime.now().getOffset())
                    .minus(1, ChronoUnit.SECONDS); // Adjust start to 1 second before the provided time
            OffsetDateTime stop = OffsetDateTime.ofInstant(time, OffsetDateTime.now().getOffset())
                    .plus(1, ChronoUnit.SECONDS); // Adjust stop to 1 second after the provided time
            String predicate = "_measurement=\"fixed_expenses\" AND creator_id = \"" + creatorId + "\"";

            deleteApi.delete(start, stop, predicate, bucket, org);
            System.out.println("Deleted fixed expense with \ncreator: "+ creatorId.toString() + " \ntime: "+time.toString());
            flag = true;
        } catch (InfluxException ie) {
            System.out.println("InfluxException while deleting fixed expenses: " + ie);
        }
        return flag;
    }

    // TODO
    private List<FixedExpenses> getFixedExpenses(QueryApi queryApi, String flux) {
        List<FixedExpenses> fixedExpenses = new ArrayList<>();
        List<FluxTable> tables = queryApi.query(flux);
        for (FluxTable fluxTable : tables) {
            List<FluxRecord> records = fluxTable.getRecords();
            for (FluxRecord fluxRecord : records) {
                FixedExpenses fixedExpense = new FixedExpenses();
                fixedExpense.setCreated((Instant) fluxRecord.getValueByKey("_time"));
                fixedExpense.set_value((Double) fluxRecord.getValueByKey("_value"));
                fixedExpense.set_field((String) fluxRecord.getValueByKey("_field"));
                fixedExpense.setCreator_id((String) fluxRecord.getValueByKey("creator_id"));
                fixedExpense.setEmployee((String) fluxRecord.getValueByKey("employee"));
                fixedExpense.setStart_date((String) fluxRecord.getValueByKey("start_date"));
                fixedExpense.setEnd_date((String) fluxRecord.getValueByKey("end_date"));
                fixedExpense.setDescription((String) fluxRecord.getValueByKey("description"));

                fixedExpenses.add(fixedExpense);
            }
        }
        return fixedExpenses;
    }

    public List<FixedExpenses> findAllFixedExpenses(InfluxDBClient influxDBClient) {
        String flux = "from(bucket:\"nais_bucket\") |> range(start:0) |> filter(fn: (r) => r[\"_measurement\"] == \"fixed_expenses\") |> sort() |> yield(name: \"sort\")";
        QueryApi queryApi = influxDBClient.getQueryApi();
        List<FixedExpenses> fixedExpenses = getFixedExpenses(queryApi, flux);
        return fixedExpenses;
    }

    public List<FixedExpenses> findAllByCreatorId(InfluxDBClient influxDBClient, String creatorId) {
        String fluxForCreator = "from(bucket:\"nais_bucket\") |> range(start:0) |> filter(fn: (r) => r[\"_measurement\"] == \"fixed_expenses\" and r[\"creator_id\"] == \"%s\") |> sort() |> yield(name: \"sort\")";
        String queryForCreator = String.format(fluxForCreator, creatorId);
        QueryApi queryApi = influxDBClient.getQueryApi();
        List<FixedExpenses> fixedExpenses = getFixedExpenses(queryApi, queryForCreator);
        return fixedExpenses;
    }
}