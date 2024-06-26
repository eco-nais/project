package rs.ac.uns.acs.nais.TimeseriesDatabaseService.repository;

import org.springframework.stereotype.Repository;
import rs.ac.uns.acs.nais.TimeseriesDatabaseService.model.FixedExpenses;

import java.time.Instant;
import java.util.List;
@Repository
public interface FixedExpensesRepository {
    Boolean save(FixedExpenses fixedExpenses);
    List<FixedExpenses> findAll();
    List<FixedExpenses> findAllByCreator(String creator_id);
    Boolean softDelete(String creator_id, Instant created);
}
