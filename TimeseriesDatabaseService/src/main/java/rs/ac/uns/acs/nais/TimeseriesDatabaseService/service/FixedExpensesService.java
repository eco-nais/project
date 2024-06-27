package rs.ac.uns.acs.nais.TimeseriesDatabaseService.service;

import org.springframework.stereotype.Service;
import rs.ac.uns.acs.nais.TimeseriesDatabaseService.model.FixedExpenses;
import rs.ac.uns.acs.nais.TimeseriesDatabaseService.repository.FixedExpensesRepository;

import java.time.Instant;
import java.util.List;

@Service
public class FixedExpensesService {

    private final FixedExpensesRepository fixedExpensesRepository;

    public FixedExpensesService(FixedExpensesRepository fixedExpensesRepository) {
        this.fixedExpensesRepository = fixedExpensesRepository;
    }

    public boolean save(FixedExpenses fixedExpenses) {
        return fixedExpensesRepository.save(fixedExpenses);
    }

    public boolean delete(String creatorId, Instant createdOn) {
        return fixedExpensesRepository.delete(creatorId, createdOn);
    }

    public List<FixedExpenses> findAll() {
        System.out.println("All fixed expenses found!");
        return fixedExpensesRepository.findAll();
    }

    public List<FixedExpenses> findAllByCreator(String creatorId) {
        return fixedExpensesRepository.findAllByCreator(creatorId);
    }
}
