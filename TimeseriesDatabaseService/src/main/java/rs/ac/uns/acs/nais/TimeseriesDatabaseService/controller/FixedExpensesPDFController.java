package rs.ac.uns.acs.nais.TimeseriesDatabaseService.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.acs.nais.TimeseriesDatabaseService.service.FixedExpensesPDFService;

import java.io.IOException;

@RestController
@RequestMapping("/pdfs")
public class FixedExpensesPDFController {

    private final FixedExpensesPDFService service;

    public FixedExpensesPDFController(FixedExpensesPDFService pdfService) {
        this.service = pdfService;
    }

    @GetMapping(value = "/simple", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<Resource> generateSimpleFixedExpensesPDF(
            @RequestParam(name = "filename", required = false, defaultValue = "generated.pdf") String filename
    ) {
        try {
            Resource result = service.generateSimpleFixedExpensesPDF();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                    .body(result);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping(value = "/simple2", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<Resource> generateSimple2FixedExpensesPDF(
            @RequestParam(name = "filename", required = false, defaultValue = "generated.pdf") String filename
    ) {
        try {
            Resource result = service.generateSimple2FixedExpensesPDF();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                    .body(result);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping(value = "/average-salary", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<Resource> generateAverageSalaryPDF(
            @RequestParam(name = "filename", required = false, defaultValue = "generated.pdf") String filename
    ) {
        try {
            Resource result = service.generateAverageSalaryPDF();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                    .body(result);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
