package com.eco.environet.finance.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/fixed-expenses")
@Tag(name = "Fixed Expenses", description = "Manage Fixed Expenses")
public class FixedExpensesController {
    private final RestTemplate restTemplate;
    private String timeseriesServiceUrl = "http://host.docker.internal:9090";
//    @Value("${timeseries.service.url}")
//    private String timeseriesServiceUrl; // cant i just put http://host.docker.internal:9090 here

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

//    @Operation(summary = "Get all fixed expenses")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Fetched all fixed expenses",
//                    content = { @Content(mediaType = "application/json",
//                            schema = @Schema(implementation = Page.class)) }),
//            @ApiResponse(responseCode = "400", description = "Bad Request",
//                    content = @Content),
//            @ApiResponse(responseCode = "401", description = "Unauthorized",
//                    content = @Content),
//            @ApiResponse(responseCode = "403", description = "Forbidden",
//                    content = @Content),
//            @ApiResponse(responseCode = "404", description = "Not Found",
//                    content = @Content),
//            @ApiResponse(responseCode = "500", description = "Internal Server Error",
//                    content = @Content)
//    })
//    @GetMapping(value="/all")
//    @PreAuthorize("hasAnyRole('BOARD_MEMBER', 'ACCOUNTANT')")
//    public ResponseEntity<Page<FixedExpensesDto>> getAllFixedExpenses(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "5") int size,
//            @RequestParam(name = "sort", required = false, defaultValue = "type") String sortField,
//            @RequestParam(name = "direction", required = false, defaultValue = "asc") String sortDirection,
//            @RequestParam(name = "period", required = false) String period,
//            @RequestParam(name = "types", required = false) List<String> types,
//            @RequestParam(name = "employees", required = false) List<Long> employees,
//            @RequestParam(name = "creators", required = false) List<Long> creators
//    ) {
//        Sort sort = Sort.by(sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortField);
//        PageRequest pageRequest = PageRequest.of(page, size, sort);
//
//        var result = service.findAll(period, types, employees, creators, pageRequest);
//        return ResponseEntity.status(HttpStatus.OK).body(result);
//    }
}
