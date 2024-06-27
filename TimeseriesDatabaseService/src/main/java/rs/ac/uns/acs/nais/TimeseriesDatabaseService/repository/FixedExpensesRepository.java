package rs.ac.uns.acs.nais.TimeseriesDatabaseService.repository;

import org.springframework.stereotype.Repository;
import rs.ac.uns.acs.nais.TimeseriesDatabaseService.model.FixedExpenses;

import java.time.Instant;
import java.util.List;
@Repository
public interface FixedExpensesRepository {
    Boolean save(FixedExpenses fixedExpenses);
    Boolean delete(String creatorId, Instant time);
    List<FixedExpenses> monthlySum(String startDate, String field);
    List<FixedExpenses> aggregateByCreator(String startDate, String endDate, String field);
    List<FixedExpenses> averageSalary(String startDate, String endDate);
}
