package com.eco.environet.projects.controller;

import com.eco.environet.projects.dto.DocumentTaskDto;
import com.eco.environet.projects.model.Task;
import com.eco.environet.projects.service.DocumentManagementService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/projects/assignments")
@Tag(name = "Assignment Management", description = "Manage documentation assignment")
public class AssignmentController {

    private final DocumentManagementService documentManagementService;

    @Operation(summary = "Get documents assigned to user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched assigned documents", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "text/plain"))
    })
    @GetMapping
    @PreAuthorize("authentication.principal.role.isOrganizationMember() and #userId == authentication.principal.id")
    public ResponseEntity<List<DocumentTaskDto>> getAssignedDocuments(@RequestParam Long userId) {
        List<DocumentTaskDto> result = documentManagementService.getAssignedDocuments(userId);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Check if user is assigned writer for document")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Checked if user is assigned writer for document", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "text/plain"))
    })
    @GetMapping("/writers")
    @PreAuthorize("authentication.principal.role.isOrganizationMember() and #userId == authentication.principal.id")
    public ResponseEntity<Task> getIfWriter(@RequestParam Long projectId, @RequestParam Long documentId, @RequestParam Long userId) {
        Task result = documentManagementService.getAssignment(projectId, documentId, userId);
        return ResponseEntity.ok(result);
    }
}