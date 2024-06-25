package com.eco.environet.finance.services.impl;

import com.eco.environet.finance.dto.FixedExpensesDto;
import com.eco.environet.finance.dto.RevenueDto;
import com.eco.environet.finance.services.FinancePDFGeneratorService;
import com.eco.environet.util.PDFGenerator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FinancePDFGeneratorServiceImpl implements FinancePDFGeneratorService {
    @Value("${baseFrontUrl}")
    private String baseFrontUrl;

    private final Map<String, String> fixedExpensesColumnMappings = Map.of(
            "number", "#",
            "id", "Id",
            "type", "Type",
            "period", "Period",
            "amount", "Amount",
            "creator", "Created by",
            "createdOn", "Created on date",
            "description", "Description",
            "employee", "Employee",
            "overtimeHours", "Overtime hours"
    );

    @Override
    public Resource generateFixedExpensesPDF(List<FixedExpensesDto> expenses, String documentTitle, String text, List<String> columns) throws IOException {
        if (columns == null) {
            columns = List.of("number", "type", "description", "amount");
        }
        columns = reorderColumns(columns);
        double totalAmount = calculateTotalAmount(expenses);
        return PDFGenerator.generatePDF(expenses, documentTitle, text, columns, fixedExpensesColumnMappings, "Total Amount:" + totalAmount);
    }

    private final Map<String, String> revenuesColumnMappings = Map.of(
            "number", "#",
            "id", "Id",
            "type", "Type",
            "amount", "Amount",
            "createdOn", "Created on date",
            "donator", "Donator",
            "project", "Project"
    );

    @Override
    public Resource generateRevenuePDF(List<RevenueDto> revenues, String documentTitle, String text, List<String> columns) throws IOException {
        if (columns == null) {
            columns = List.of("number", "type", "createdOn", "amount");
        }
        columns = reorderColumns(columns);
        double totalAmount = calculateTotalAmount(revenues);
        return PDFGenerator.generatePDF(revenues, documentTitle, text, columns, revenuesColumnMappings, "Total Amount:" + totalAmount);
    }

    private List<String> reorderColumns(List<String> columns) {
        List<String> reorderedColumns = new ArrayList<>(columns);
        if (!reorderedColumns.contains("amount")) {
            reorderedColumns.add("amount");
        } else {
            reorderedColumns.remove("amount");
            reorderedColumns.add("amount");
        }
        return reorderedColumns;
    }

    private double calculateTotalAmount(List<? extends Object> items) {
        double total = 0;
        for (Object item : items) {
            double amount = 0;
            if (item instanceof FixedExpensesDto) {
                amount = ((FixedExpensesDto) item).getAmount();
            } else if (item instanceof RevenueDto) {
                amount = ((RevenueDto) item).getAmount();
            }
            total += amount;
        }
        // Round the total amount to a maximum of 4 digits
        total = Math.round(total * 10000.0) / 10000.0;
        return total;
    }
}
