package rs.ac.uns.acs.nais.TimeseriesDatabaseService.repository;

import com.influxdb.client.InfluxDBClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import rs.ac.uns.acs.nais.TimeseriesDatabaseService.configuration.InfluxDBConnectionClass;
import rs.ac.uns.acs.nais.TimeseriesDatabaseService.model.FixedExpenses;

import java.time.Instant;
import java.util.List;

@Repository
public class FixedExpensesRepositoryImpl implements FixedExpensesRepository {

    private final InfluxDBConnectionClass inConn;

    @Autowired
    public FixedExpensesRepositoryImpl(InfluxDBConnectionClass influxDBConnectionClass) {
        this.inConn = influxDBConnectionClass;
    }

    @Override
    public Boolean save(FixedExpenses fixedExpenses) {
        InfluxDBClient influxDBClient = inConn.buildConnection();
        Boolean isSuccess = inConn.saveFixedExpense(influxDBClient, fixedExpenses);
        influxDBClient.close();
        return isSuccess;
    }

    @Override
    public Boolean delete(String creatorId, Instant time) {
        InfluxDBClient influxDBClient = inConn.buildConnection();
        Boolean isSuccess = inConn.deleteFixedExpenses(influxDBClient, creatorId, time);
        influxDBClient.close();
        return isSuccess;
    }

    @Override
    public List<FixedExpenses> monthlySum(String startDate, String field) {
        InfluxDBClient influxDBClient = inConn.buildConnection();
        List<FixedExpenses> result = inConn.monthlySum(influxDBClient, startDate, field);
        influxDBClient.close();
        return result;
    }

    @Override
    public List<FixedExpenses> aggregateByCreator(String startDate, String endDate, String field) {
        InfluxDBClient influxDBClient = inConn.buildConnection();
        List<FixedExpenses> result = inConn.aggregateByCreator(influxDBClient, startDate, endDate, field);
        influxDBClient.close();
        return result;
    }
}
