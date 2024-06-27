package com.eco.environet.finance.controller;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import com.eco.environet.finance.dto.FixedExpensesDto;
import com.eco.environet.finance.dto.TimeseriesFixedExpensesDto;
import com.eco.environet.finance.dto.FixedExpensesMapper;
import com.eco.environet.finance.services.FixedExpensesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/fixed-expenses")
@Tag(name = "Fixed Expenses", description = "Manage Fixed Expenses")
public class FixedExpensesController {
    private final RestTemplate restTemplate;
    private String timeseriesServiceUrl = "http://host.docker.internal:9090";

    public FixedExpensesController(FixedExpensesService service) {
        this.restTemplate = new RestTemplate();
        this.timeseriesServiceUrl = "http://localhost:9090";
    }

    @Operation(summary = "Create new fixed expense")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New fixed expense created!",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FixedExpensesDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content)
    })
    @PostMapping(value = "/create", consumes = "application/json")
    @PreAuthorize("hasRole('ACCOUNTANT')")
    public ResponseEntity<FixedExpensesDto> createNewFixedExpense(@RequestBody FixedExpensesDto newExpenseDto) {
        // Forward the request to the TimeseriesDatabaseService
        String url = timeseriesServiceUrl + "/fixed-expenses.json/save";
        // Map FixedExpensesDto to TimeseriesFixedExpensesDto
        TimeseriesFixedExpensesDto timeseriesFixedExpensesDto = FixedExpensesMapper.toTimeseriesFixedExpensesDto(newExpenseDto);

        HttpEntity<TimeseriesFixedExpensesDto> request = new HttpEntity<>(timeseriesFixedExpensesDto);
        ResponseEntity<Boolean> response = restTemplate.exchange(url, HttpMethod.POST, request, Boolean.class);

        if (response.getBody() != null && response.getBody()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(newExpenseDto);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(summary = "Delete fixed expense by creator ID and created date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fixed expense soft deleted!",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content)
    })
    @DeleteMapping(value = "/delete")
    @PreAuthorize("hasRole('ACCOUNTANT')")
    public ResponseEntity<Boolean> softDeleteFixedExpense(@RequestParam("creatorId") String creatorId,
                                                          @RequestParam("createdOn") Instant createdOn) {
        // Forward the delete request to the TimeseriesDatabaseService
        String url = timeseriesServiceUrl + "/fixed-expenses.json/delete?creatorId=" + creatorId + "&createdOn=" + createdOn.toString();

        ResponseEntity<Boolean> response = restTemplate.exchange(url, HttpMethod.DELETE, null, Boolean.class);

        if (response.getBody() != null && response.getBody()) {
            return ResponseEntity.ok().body(true);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Get monthly summary of fixed expenses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Monthly summary retrieved successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FixedExpensesDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content)
    })
    @GetMapping(value = "/monthly-summary", produces = "application/json")
    @PreAuthorize("hasRole('ACCOUNTANT')")
    public ResponseEntity<List<TimeseriesFixedExpensesDto>> getMonthlySummary(
            @RequestParam(value = "start_date", defaultValue = "0") String startDate,
            @RequestParam(value = "field", defaultValue = "SALARY") String field) {
        // Forward the request to the TimeseriesDatabaseService
        String url = timeseriesServiceUrl + "/fixed-expenses.json/monthly?start_date=" + startDate + "&field=" + field;

        ResponseEntity<List<TimeseriesFixedExpensesDto>> response = restTemplate.exchange(
                url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<TimeseriesFixedExpensesDto>>() {});

        if (response.getBody() != null) {
            return ResponseEntity.ok(response.getBody());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Get monthly aggregation of fixed expenses for creators")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aggregate By Creator retrieved successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FixedExpensesDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content)
    })
    @GetMapping(value = "/aggregate-by-creator", produces = "application/json")
    @PreAuthorize("hasRole('ACCOUNTANT')")
    public ResponseEntity<List<TimeseriesFixedExpensesDto>> getAggregateByCreator(
            @RequestParam(value = "start_date", defaultValue = "0") String startDate,
            @RequestParam(value = "end_date", required = false) String endDate,
            @RequestParam(value = "field", defaultValue = "SALARY") String field) {
        // Forward the request to the TimeseriesDatabaseService
        String url = timeseriesServiceUrl + "/fixed-expenses.json/aggregateByCreator?field=" + field +
                "&start_date=" + startDate;

        // Append endDate if provided
        if (endDate != null) {
            url += "&end_date=" + endDate;
        }

        ResponseEntity<List<TimeseriesFixedExpensesDto>> response = restTemplate.exchange(
                url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<TimeseriesFixedExpensesDto>>() {});

        if (response.getBody() != null) {
            return ResponseEntity.ok(response.getBody());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Get Average Salary for employees")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Average Salary retrieved successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FixedExpensesDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content)
    })
    @GetMapping(value = "/average-salary", produces = "application/json")
    @PreAuthorize("hasRole('ACCOUNTANT')")
    public ResponseEntity<List<TimeseriesFixedExpensesDto>> getAverageSalary(
            @RequestParam(value = "start_date", defaultValue = "0") String startDate,
            @RequestParam(value = "end_date", required = false) String endDate) {
        // Forward the request to the TimeseriesDatabaseService
        String url = timeseriesServiceUrl + "/fixed-expenses.json/averageSalary?start_date=" + startDate;

        // Append endDate if provided
        if (endDate != null) {
            url += "&end_date=" + endDate;
        }

        ResponseEntity<List<TimeseriesFixedExpensesDto>> response = restTemplate.exchange(
                url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<TimeseriesFixedExpensesDto>>() {});

        if (response.getBody() != null) {
            return ResponseEntity.ok(response.getBody());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Generate simple PDF report for Fixed Expenses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Generated simple PDF report for Fixed Expenses", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "text/plain"))
    })
    @PreAuthorize("hasRole('ACCOUNTANT')")
    @GetMapping(value = "/pdf/fixed-expenses", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<Resource> generateSimpleFixedExpensesPDF(
            @RequestParam(name = "filename", required = false, defaultValue = "generated.pdf") String filename
    ) {
        String url = timeseriesServiceUrl + "/pdfs/simple?filename=" + filename;
        //String url = timeseriesServiceUrl + "/fixed-expenses.json/averageSalary?start_date=" + startDate;

        // Send GET request to generate PDF
        ResponseEntity<Resource> response = restTemplate.exchange(
                url, HttpMethod.GET, null, Resource.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(response.getBody());
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Generate second simple PDF report for Fixed Expenses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Generated second simple PDF report for Fixed Expenses", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "text/plain"))
    })
    @PreAuthorize("hasRole('ACCOUNTANT')")
    @GetMapping(value = "/pdf/fixed-expenses-2", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<Resource> generateSimple2FixedExpensesPDF(
            @RequestParam(name = "filename", required = false, defaultValue = "generated.pdf") String filename
    ) {
        String url = timeseriesServiceUrl + "/pdfs/simple2?filename=" + filename;
        //String url = timeseriesServiceUrl + "/fixed-expenses.json/averageSalary?start_date=" + startDate;

        // Send GET request to generate PDF
        ResponseEntity<Resource> response = restTemplate.exchange(
                url, HttpMethod.GET, null, Resource.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(response.getBody());
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Generate average salary PDF report")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Generated average salary PDF report",
                    content = @Content(mediaType = "application/json"))
    })
    @PreAuthorize("hasRole('ACCOUNTANT')")
    @GetMapping(value = "/pdf/average-salary", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<Resource> generateAverageSalaryPDF(
            @RequestParam(name = "filename", required = false, defaultValue = "average_salary.pdf") String filename
    ) {
        String url = timeseriesServiceUrl + "/pdfs/average-salary?filename=" + filename;

        // Send GET request to generate PDF
        ResponseEntity<Resource> response = restTemplate.exchange(
                url, HttpMethod.GET, null, Resource.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(response.getBody());
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
