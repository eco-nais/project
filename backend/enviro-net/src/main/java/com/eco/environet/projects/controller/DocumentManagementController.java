package com.eco.environet.projects.controller;

import com.eco.environet.projects.dto.DocumentCreationDto;
import com.eco.environet.projects.dto.DocumentDto;
import com.eco.environet.projects.dto.DocumentUploadDto;
import com.eco.environet.projects.dto.DocumentVersionsDto;
import com.eco.environet.projects.service.DocumentManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/projects/{projectId}/documents")
@Tag(name = "Document Management", description = "Manage project documentation")
public class DocumentManagementController {

    private final DocumentManagementService documentManagementService;

    @Operation(summary = "Add project document")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Document added", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "text/plain"))
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('PROJECT_MANAGER') and @projectRepository.findById(#projectId).orElse(null)?.manager?.id == authentication.principal.id")
    public ResponseEntity<DocumentDto> createDocument(
            @PathVariable Long projectId,
            @ModelAttribute @Valid DocumentCreationDto documentDto
    ) throws IOException {
        DocumentDto result = documentManagementService.createDocument(projectId, documentDto);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Delete project document")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Document deleted", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "text/plain"))
    })
    @DeleteMapping(value = "/{documentId}")
    @PreAuthorize("hasRole('PROJECT_MANAGER') and @projectRepository.findById(#projectId).orElse(null)?.manager?.id == authentication.principal.id")
    public ResponseEntity<Void> deleteDocument(
            @PathVariable Long projectId,
            @PathVariable Long documentId
    ) {
        documentManagementService.deleteDocument(projectId, documentId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Upload document version")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Document uploaded", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "text/plain"))
    })
    @PutMapping(value = "/{documentId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("authentication.principal.role.isOrganizationMember() and #documentDto.userId == authentication.principal.id")
    public ResponseEntity<DocumentDto> uploadVersion(
            @PathVariable Long projectId,
            @PathVariable Long documentId,
            @ModelAttribute @Valid DocumentUploadDto documentDto
    ) throws IOException {
        DocumentDto result = documentManagementService.uploadDocument(projectId, documentId, documentDto);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Get all document versions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched all document versions", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "text/plain"))
    })
    @GetMapping(value = "/{documentId}/versions")
    @PreAuthorize("authentication.principal.role.isOrganizationMember() and @teamMemberRepository.isOnTeam(#projectId, authentication.principal.id)")
    public ResponseEntity<DocumentVersionsDto> getDocumentVersions(@PathVariable Long projectId, @PathVariable Long documentId)
    {
        DocumentVersionsDto result = documentManagementService.getDocumentVersions(projectId, documentId);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Get document file by version")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched document file", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "text/plain"))
    })
    @GetMapping(value = "/{documentId}/versions/{version}")
    @PreAuthorize("authentication.principal.role.isOrganizationMember() and @teamMemberRepository.isOnTeam(#projectId, authentication.principal.id)")
    public ResponseEntity<Resource> getDocumentVersion(@PathVariable Long projectId, @PathVariable Long documentId, @PathVariable Long version) throws IOException
    {
        Resource result = documentManagementService.getDocumentFile(projectId, documentId, version);
        String contentType = Files.probeContentType(Paths.get(result.getURI()));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + result.getFilename() + "\"")
                .contentType(MediaType.parseMediaType(contentType))
                .body(result);
    }
}
