package com.eco.environet.finance.services;

import com.eco.environet.finance.dto.FixedExpensesDto;
import com.eco.environet.finance.dto.RevenueDto;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;

public interface FinancePDFGeneratorService {
    Resource generateFixedExpensesPDF(List<FixedExpensesDto> expenses, String documentTitle, String text, List<String> columns) throws IOException;
    Resource generateRevenuePDF(List<RevenueDto> revenues, String documentTitle, String text, List<String> columns) throws IOException;
}
