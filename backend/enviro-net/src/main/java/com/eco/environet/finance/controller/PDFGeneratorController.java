package com.eco.environet.finance.controller;

import java.io.IOException;
import java.util.List;

import com.eco.environet.finance.dto.FixedExpensesDto;
import com.eco.environet.finance.dto.RevenueDto;
import com.eco.environet.finance.services.FinancePDFGeneratorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/finance-generate-pdf")
@RequiredArgsConstructor
@Tag(name = "Accountant PDFs", description = "Manage Accountant's PDF generation")
public class PDFGeneratorController {
    private final FinancePDFGeneratorService generatorService;

//    @Operation(summary = "Generate PDF report for Fixed Expenses")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Generated PDF report for Fixed Expenses", content = @Content(mediaType = "application/json")),
//            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "text/plain"))
//    })
//    @PreAuthorize("hasAnyRole('BOARD_MEMBER', 'ACCOUNTANT')")
//    @PostMapping(value = "/fixed-expenses", consumes = "application/json")
//    public ResponseEntity<Resource> createNewRevenue(
//            @RequestBody List<FixedExpensesDto> fixedExpensesDtos,
//            @RequestParam(name = "filename", required = false, defaultValue = "generated.pdf") String filename,
//            @RequestParam(name = "columns", required = false) List<String> columns,
//            @RequestParam(name = "title", required = false, defaultValue = "Generated PDF") String title,
//            @RequestParam(name = "text", required = false) String text
//    ) {
//        try {
//            Resource result = generatorService.generateFixedExpensesPDF(fixedExpensesDtos, title ,text, columns);
//
//            return ResponseEntity.ok()
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
//                    .contentType(MediaType.APPLICATION_PDF)
//                    .body(result);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }

    @Operation(summary = "Generate PDF report for Revenues")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Generated PDF report for Revenues", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "text/plain"))
    })
    @PreAuthorize("hasAnyRole('BOARD_MEMBER', 'ACCOUNTANT')")
    @PostMapping(value = "/revenues", consumes = "application/json")
    public ResponseEntity<Resource> generateRevenuePDF(
            @RequestBody List<RevenueDto> revenueDtos,
            @RequestParam(name = "filename", required = false, defaultValue = "generated.pdf") String filename,
            @RequestParam(name = "columns", required = false) List<String> columns,
            @RequestParam(name = "title", required = false, defaultValue = "Generated PDF") String title,
            @RequestParam(name = "text", required = false) String text
    ) {
        try {
            Resource result = generatorService.generateRevenuePDF(revenueDtos, title, text, columns);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(result);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
