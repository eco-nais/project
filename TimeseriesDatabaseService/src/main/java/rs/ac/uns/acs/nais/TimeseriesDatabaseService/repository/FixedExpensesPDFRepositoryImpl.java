package rs.ac.uns.acs.nais.TimeseriesDatabaseService.repository;

import com.influxdb.client.InfluxDBClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;
import rs.ac.uns.acs.nais.TimeseriesDatabaseService.configuration.InfluxDBConnectionClass;
import rs.ac.uns.acs.nais.TimeseriesDatabaseService.model.FixedExpenses;
import rs.ac.uns.acs.nais.TimeseriesDatabaseService.util.PDFGenerator;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Repository
public class FixedExpensesPDFRepositoryImpl implements FixedExpensesPDFRepository {
    private final InfluxDBConnectionClass inConn;

    @Autowired
    public FixedExpensesPDFRepositoryImpl(InfluxDBConnectionClass influxDBConnectionClass) {
        this.inConn = influxDBConnectionClass;
    }

    private final Map<String, String> fixedExpensesColumnMappings = Map.of(
            "number", "#",
            "_field", "Type",
            "start_date", "Start date",
            "end_date", "End date",
            "_value", "Amount",
            "creator_id", "Created by",
            "created", "Created on date",
            "description", "Description",
            "employee", "Employee"
    );

    private double calculateTotalAmount(List<FixedExpenses> items) {
        double total = 0;
        for (FixedExpenses item : items) {
            double amount = 0;
            amount = item.get_value();
            total += amount;
        }
        // Round the total amount to a maximum of 4 digits
        total = Math.round(total * 10000.0) / 10000.0;
        return total;
    }
    private List<FixedExpenses> simpleQueryForPDF() {
        InfluxDBClient influxDBClient = inConn.buildConnection();
        List<FixedExpenses> result = inConn.simpleQueryForPDF(influxDBClient);
        influxDBClient.close();
        return result;
    }
    @Override
    public Resource generateSimpleFixedExpensesPDF() throws IOException {
        List<FixedExpenses> result = simpleQueryForPDF();
        List<String> columns = List.of("number", "_field", "description", "_value");
        String text = "Generated simple PDF. \nThis query fetches fixed expenses data from the 'fixed_expenses' measurement in the 'nais_bucket' InfluxDB database, sorted in ascending order based on the _value field";
        String title = "Fixed Expenses PDF - simple";
        double totalAmount = calculateTotalAmount(result);
        return PDFGenerator.generatePDF(result, title, text, columns, fixedExpensesColumnMappings, "Total Amount:" + totalAmount);
    }

    private List<FixedExpenses> averageSalary() {
        InfluxDBClient influxDBClient = inConn.buildConnection();
        List<FixedExpenses> result = inConn.averageSalary(influxDBClient, null, null);
        influxDBClient.close();
        return result;
    }
    @Override
    public Resource generateAverageSalaryPDF() throws IOException {
        List<FixedExpenses> result = averageSalary();
        List<String> columns = List.of("number", "employee", "_value");
        String paragraph = "Generated PDF for average salary of employees. \nThis PDF contains aggregated salary data from the 'fixed_expenses' measurement in the 'nais_bucket' InfluxDB database. The salaries are averaged across employees for the time range starting from 2023-01-03T00:00:00.000Z and beyond.";
        String title = "Fixed Expenses PDF - simple";
        return PDFGenerator.generatePDF(result, title, paragraph, columns, fixedExpensesColumnMappings);
    }
}
