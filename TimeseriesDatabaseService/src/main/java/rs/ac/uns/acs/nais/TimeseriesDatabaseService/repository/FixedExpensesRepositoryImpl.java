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
    public List<FixedExpenses> findAll() {
        InfluxDBClient influxDBClient = inConn.buildConnection();
        List<FixedExpenses> fixedExpenses = inConn.findAllFixedExpenses(influxDBClient);
        influxDBClient.close();
        return fixedExpenses;
    }

    @Override
    public List<FixedExpenses> findAllByCreator(String creatorId) {
        InfluxDBClient influxDBClient = inConn.buildConnection();
        List<FixedExpenses> fixedExpenses = inConn.findAllByCreatorId(influxDBClient, creatorId);
        influxDBClient.close();
        return fixedExpenses;
    }
}
