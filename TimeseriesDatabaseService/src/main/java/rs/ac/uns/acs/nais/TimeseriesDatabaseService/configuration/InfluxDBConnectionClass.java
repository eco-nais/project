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
import rs.ac.uns.acs.nais.TimeseriesDatabaseService.model.Purchase;
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

    public boolean save(InfluxDBClient influxDBClient, Purchase purchase) {
        boolean flag = false;
        try {
            WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();
            purchase.setCreated(Instant.parse("2021-10-12T05:10:15.187484Z"));
            Point point = Point.measurement("purchases")
                    .addTag("product_id", purchase.getProduct_id())
                    .addTag("customer_id", purchase.getCustomer_id())
                    .addField(purchase.get_field(), (double)purchase.get_value())
                    .time(purchase.getCreated(), WritePrecision.MS);

            writeApi.writePoint(point);
            System.out.println("Sacuvao");
            flag = true;
        } catch (InfluxException e) {
            System.out.println("Exception!!" + e.getMessage());
        }
        return flag;
    }

    public boolean writePointbyPOJO(InfluxDBClient influxDBClient, Purchase purchase) {
        boolean flag = false;
        try {
            WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();

            purchase.setCreated(Instant.parse("2021-10-12T05:10:15.187484Z"));

            writeApi.writeMeasurement(WritePrecision.MS, purchase);
            flag = true;
        } catch (

                InfluxException e) {
            System.out.println("Exception!!" + e.getMessage());
        }
        return flag;
    }

    public List<Purchase> findAll(InfluxDBClient influxDBClient) {
        String flux = "from(bucket:\"nais_bucket\") |> range(start:0) |> filter(fn: (r) => r[\"_measurement\"] == \"purchases\") |> sort() |> yield(name: \"sort\")";
        QueryApi queryApi = influxDBClient.getQueryApi();
        List<Purchase> purchases = getPurchases(queryApi, flux);
        return purchases;
    }

    public List<Purchase> findAllByCustomerId(InfluxDBClient influxDBClient, String customerId) {
        String fluxForCustomer = "from(bucket:\"nais_bucket\") |> range(start:0) |> filter(fn: (r) => r[\"_measurement\"] == \"purchases\" and r[\"customer_id\"] == \"%s\") |> sort() |> yield(name: \"sort\")";
        String queryForCustomer = String.format(fluxForCustomer, customerId);
        QueryApi queryApi = influxDBClient.getQueryApi();
        List<Purchase> purchases = getPurchases(queryApi, queryForCustomer);
        return purchases;
    }

    public List<Purchase> findAllByProductId(InfluxDBClient influxDBClient, String productId) {
        String fluxForProduct = "from(bucket:\"nais_bucket\") |> range(start:0) |> filter(fn: (r) => r[\"_measurement\"] == \"purchases\" and r[\"product_id\"] == \"%s\") |> sort() |> yield(name: \"sort\")";
        String queryForProduct = String.format(fluxForProduct, productId);
        QueryApi queryApi = influxDBClient.getQueryApi();
        List<Purchase> purchases = getPurchases(queryApi, queryForProduct);
        return purchases;
    }


    private List<Purchase> getPurchases(QueryApi queryApi, String flux) {
        List<Purchase> purchases = new ArrayList<>();
        List<FluxTable> tables = queryApi.query(flux);
        for (FluxTable fluxTable : tables) {
            List<FluxRecord> records = fluxTable.getRecords();
            for (FluxRecord fluxRecord : records) {
                Purchase purchase = new Purchase();
                purchase.setCustomer_id((String) fluxRecord.getValueByKey("customer_id"));
                purchase.setProduct_id((String) fluxRecord.getValueByKey("product_id"));
                purchase.set_field((String) fluxRecord.getValueByKey("_field"));
                purchase.set_value((Double) fluxRecord.getValueByKey("_value"));
                purchase.setCreated((Instant) fluxRecord.getValueByKey("_time"));
                purchases.add(purchase);
            }
        }
        return purchases;
    }

    public boolean deleteRecord(InfluxDBClient influxDBClient, String productId) {
        boolean flag = false;
        DeleteApi deleteApi = influxDBClient.getDeleteApi();

        try {

            OffsetDateTime start = OffsetDateTime.now().minus(72, ChronoUnit.HOURS);
            OffsetDateTime stop = OffsetDateTime.now();
            String predicate = "_measurement=\"purchases\" AND product_id = \"" +
                    productId +
                    "\"";

            deleteApi.delete(start, stop, predicate, bucket, org);

            flag = true;
        } catch (InfluxException ie) {
            System.out.println("InfluxException: " + ie);
        }
        return flag;
    }

    // FixedExpenses
    public boolean saveFixedExpense(InfluxDBClient influxDBClient, FixedExpenses fixedExpense) {
        boolean flag = false;
        try {
            WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();
            fixedExpense.setCreated(Instant.now()); // Set current time as createdOn timestamp

            Point point = Point.measurement("fixed_expenses")
                    .addField("_value", fixedExpense.get_value())
                    .addField("_field", fixedExpense.get_field())
                    .addTag("creator_id", fixedExpense.getCreator_id())
                    .addField("deleted", fixedExpense.getDeleted())
                    .addTag("employee", fixedExpense.getEmployee().toString()) // Add employee as a tag
                    .addField("_overtime_hours", fixedExpense.get_overtime_hours()) // Add overtime_hours as a field
                    .addField("_start_date", fixedExpense.get_start_date())
                    .addField("_end_date", fixedExpense.get_end_date())
                    .addField("_description", fixedExpense.get_description())

                    .time(fixedExpense.getCreated(), WritePrecision.MS);

            writeApi.writePoint(point);
            System.out.println("Saved new fixed expense!");
            flag = true;
        } catch (InfluxException e) {
            System.out.println("Exception while saving fixed expense: " + e.getMessage());
        }
        return flag;
    }

    public boolean deleteFixedExpensesByCreatorId(InfluxDBClient influxDBClient, String creatorId) {
        boolean flag = false;
        DeleteApi deleteApi = influxDBClient.getDeleteApi();

        try {
            OffsetDateTime start = OffsetDateTime.now().minus(72, ChronoUnit.HOURS);
            OffsetDateTime stop = OffsetDateTime.now();
            String predicate = "_measurement=\"fixed_expenses\" AND creator_id = \"" +
                    creatorId +
                    "\"";

            deleteApi.delete(start, stop, predicate, bucket, org);

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
                fixedExpense.setDeleted((Boolean) fluxRecord.getValueByKey("deleted"));
                fixedExpense.setEmployee((Integer) fluxRecord.getValueByKey("employee"));
                fixedExpense.set_overtime_hours((Double) fluxRecord.getValueByKey("_overtime_hours"));
                fixedExpense.set_start_date((String) fluxRecord.getValueByKey("_start_date"));
                fixedExpense.set_end_date((String) fluxRecord.getValueByKey("_end_date"));
                fixedExpense.set_description((String) fluxRecord.getValueByKey("_description"));

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