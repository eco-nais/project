package rs.ac.uns.acs.nais.TimeseriesDatabaseService.repository;

import org.springframework.core.io.Resource;
import rs.ac.uns.acs.nais.TimeseriesDatabaseService.model.FixedExpenses;

import java.io.IOException;
import java.util.List;
public interface FixedExpensesPDFRepository {
    Resource generateSimpleFixedExpensesPDF() throws IOException;
    Resource generateSimple2FixedExpensesPDF() throws IOException;
    Resource generateAverageSalaryPDF() throws IOException;
}
