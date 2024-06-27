package rs.ac.uns.acs.nais.TimeseriesDatabaseService.service;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import rs.ac.uns.acs.nais.TimeseriesDatabaseService.repository.FixedExpensesPDFRepository;

import java.io.IOException;

@Service
public class FixedExpensesPDFService {
    private final FixedExpensesPDFRepository repository;

    public FixedExpensesPDFService(FixedExpensesPDFRepository repo) {
        this.repository = repo;
    }

    public Resource generateSimpleFixedExpensesPDF() throws IOException {
        return repository.generateSimpleFixedExpensesPDF();
    }

    public Resource generateAverageSalaryPDF() throws IOException {
        return repository.generateAverageSalaryPDF();
    }
}
