package com.eco.environet.projects.controller;

import com.eco.environet.projects.dto.RankedProjectDto;
import com.eco.environet.projects.service.RankedProjectsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/ranked-projects")
@Tag(name = "Ranked Project Overview", description = "Overview ranked projects")
public class RankedProjectsController {
    private final RankedProjectsService rankedProjectsService;

    @Operation(summary = "Fetch projects with basic info and rank")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched ranked projects with basic info", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "text/plain"))
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('PROJECT_MANAGER', 'ACCOUNTANT', 'BOARD_MEMBER')")
    public ResponseEntity<List<RankedProjectDto>> fetchRankedProjects() {
        var result = rankedProjectsService.findAllProjectRanks();
        return ResponseEntity.ok(result);
    }
}
