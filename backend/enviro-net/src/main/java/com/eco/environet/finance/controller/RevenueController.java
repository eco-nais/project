package com.eco.environet.finance.controller;

import com.eco.environet.finance.dto.RevenueDto;
import com.eco.environet.finance.services.RevenueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/revenues")
@RequiredArgsConstructor
@Tag(name = "Revenue", description = "Manage Revenue")
public class RevenueController {
    private final RevenueService service;

    @Operation(summary = "Create new revenue")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New revenue created!",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RevenueDto.class)) }),
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
    @PreAuthorize("hasAnyRole('BOARD_MEMBER', 'ACCOUNTANT')")
    @PostMapping(value = "/create", consumes = "application/json")
    public ResponseEntity<RevenueDto> createNewRevenue(@RequestBody RevenueDto newRevenueDto) {
        var result = service.create(newRevenueDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Operation(summary = "Get revenue")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched revenue",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RevenueDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content)})
    @GetMapping(value="/get/{id}")
    @PreAuthorize("hasAnyRole('BOARD_MEMBER', 'ACCOUNTANT')")
    public ResponseEntity<RevenueDto> getRevenue(@PathVariable Long id){
        var result = service.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Operation(summary = "Get all revenues")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched all revenues",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class)) }),
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
    @GetMapping(value="/all")
    @PreAuthorize("hasAnyRole('BOARD_MEMBER', 'ACCOUNTANT')")
    public ResponseEntity<Page<RevenueDto>> getAllRevenues(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(name = "types", required = false) List<String> types,
            @RequestParam(name = "startDate", required = false) String startDate,
            @RequestParam(name = "endDate", required = false) String endDate,
            @RequestParam(name = "aboveAmount", required = false, defaultValue = "0") Double aboveAmount,
            @RequestParam(name = "belowAmount", required = false, defaultValue = "0") Double belowAmount,
            @RequestParam(name = "sort", required = false, defaultValue = "type") String sortField,
            @RequestParam(name = "direction", required = false, defaultValue = "asc") String sortDirection
    ) {
        Sort sort = Sort.by(sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortField);
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        var result = service.findAll(types, startDate, endDate, aboveAmount, belowAmount, pageRequest);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Operation(summary = "Update revenue")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Revenue updated!",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RevenueDto.class)) }),
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
    @PutMapping(value = "/update")
    @PreAuthorize("hasRole('ACCOUNTANT')")
    public ResponseEntity<RevenueDto> updateRevenue(@RequestBody RevenueDto revenueDto) {
        var result = service.update(revenueDto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
