package com.eco.environet.finance.controller;

import com.eco.environet.finance.dto.TotalProjectRevenueDto;
import com.eco.environet.finance.services.ProjectRevenueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/project-revenues")
@RequiredArgsConstructor
@Tag(name = "Project Revenue", description = "Manage Project Revenue and Donations")
public class ProjectRevenueController {
    private final ProjectRevenueService service;

    @Operation(summary = "Get all project revenues")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched all project revenues",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TotalProjectRevenueDto.class)) }),
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
    @GetMapping(value="/external")
    @PreAuthorize("hasAnyRole('BOARD_MEMBER', 'ACCOUNTANT')")
    public ResponseEntity<TotalProjectRevenueDto> getAllProjectRevenues(
            @RequestParam(name = "projectId", required = true) Long projectId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(name = "sort", required = false, defaultValue = "type") String sortField,
            @RequestParam(name = "direction", required = false, defaultValue = "asc") String sortDirection
    ) {
        Sort sort = Sort.by(sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortField);
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        var result = service.findAllProjectRevenue(projectId, pageRequest);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Operation(summary = "Get all project donations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched all project donations",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TotalProjectRevenueDto.class)) }),
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
    @GetMapping(value="/internal")
    @PreAuthorize("hasAnyRole('BOARD_MEMBER', 'ACCOUNTANT')")
    public ResponseEntity<TotalProjectRevenueDto> getAllProjectDonations(
            @RequestParam(name = "projectId", required = true) Long projectId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(name = "sort", required = false, defaultValue = "type") String sortField,
            @RequestParam(name = "direction", required = false, defaultValue = "asc") String sortDirection
    ) {
        Sort sort = Sort.by(sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortField);
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        var result = service.findAllProjectDonations(projectId, pageRequest);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
