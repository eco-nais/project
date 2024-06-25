package com.eco.environet.finance.controller;

import com.eco.environet.finance.dto.ProjectBudgetDto;
import com.eco.environet.finance.services.ProjectBudgetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/project-budget")
@RequiredArgsConstructor
@Tag(name = "Project Budget", description = "Manage Project budgets")
public class ProjectBudgetController {
    private final ProjectBudgetService service;

    @Operation(summary = "Create new Project Budget")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New Project Budget created!",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProjectBudgetDto.class)) }),
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
    public ResponseEntity<ProjectBudgetDto> createNewProjectBudget(@RequestBody ProjectBudgetDto newProjectBudgetDto) {
        var result = service.create(newProjectBudgetDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Operation(summary = "Get Project Budget")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched Project Budget",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProjectBudgetDto.class)) }),
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
    public ResponseEntity<ProjectBudgetDto> getProjectBudget(@PathVariable Long id){
        var result = service.findByProjectId(id);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Operation(summary = "Update ProjectBudget")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ProjectBudget updated!",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProjectBudgetDto.class)) }),
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
    public ResponseEntity<ProjectBudgetDto> updateProjectBudget(@RequestBody ProjectBudgetDto ProjectBudgetDto) {
        var result = service.update(ProjectBudgetDto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
