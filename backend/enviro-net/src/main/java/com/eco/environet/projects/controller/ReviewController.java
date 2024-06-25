package com.eco.environet.projects.controller;

import com.eco.environet.projects.dto.DocumentReviewCreationDto;
import com.eco.environet.projects.dto.DocumentReviewDto;
import com.eco.environet.projects.dto.DocumentReviewStatusDto;
import com.eco.environet.projects.dto.DocumentTaskDto;
import com.eco.environet.projects.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/projects")
@Tag(name = "Review Management", description = "Manage document and project reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "Request document version review")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Review requested", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "text/plain"))
    })
    @PostMapping("/{projectId}/documents/{documentId}/versions/{version}/request-review")
    @PreAuthorize("authentication.principal.role.isOrganizationMember() and @teamMemberRepository.isOnTeam(#projectId, authentication.principal.id)")
    public ResponseEntity<Void> createReviewRequest(
            @PathVariable Long projectId,
            @PathVariable Long documentId,
            @PathVariable Long version) {
        reviewService.requestReview(projectId, documentId, version);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Fetch document reviews")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Document reviews fetched", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "text/plain"))
    })
    @GetMapping("/{projectId}/documents/{documentId}/reviews")
    @PreAuthorize("authentication.principal.role.isOrganizationMember() and @teamMemberRepository.isOnTeam(#projectId, authentication.principal.id)")
    public ResponseEntity<List<DocumentReviewDto>> getDocumentReviews(@PathVariable Long projectId, @PathVariable Long documentId) {
        List<DocumentReviewDto> result = reviewService.getDocumentReviews(projectId, documentId);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Fetch document reviews statuses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Document reviews statuses fetched", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "text/plain"))
    })
    @GetMapping("/{projectId}/documents/{documentId}/reviews/status")
    @PreAuthorize("authentication.principal.role.isOrganizationMember() and @teamMemberRepository.isOnTeam(#projectId, authentication.principal.id)")
    public ResponseEntity<List<DocumentReviewStatusDto>> getDocumentRequestStatuses(@PathVariable Long projectId, @PathVariable Long documentId) {
        List<DocumentReviewStatusDto> result = reviewService.getDocumentReviewStatuses(projectId, documentId);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Fetch document versions pending request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Documents versions fetched", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "text/plain"))
    })
    @GetMapping("/assignments/unreviewed")
    @PreAuthorize("authentication.principal.role.isOrganizationMember() and #userId == authentication.principal.id")
    public ResponseEntity<List<DocumentTaskDto>> getUnreviewedDocuments(@RequestParam Long userId) {
        List<DocumentTaskDto> result = reviewService.getUnreviewedDocuments(userId);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Review document version")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Document version reviewed", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "text/plain"))
    })
    @PostMapping("/{projectId}/documents/{documentId}/versions/{version}/review")
    @PreAuthorize("authentication.principal.role.isOrganizationMember() and #reviewDto.userId == authentication.principal.id")
    public ResponseEntity<Void> createReview(
            @PathVariable Long projectId,
            @PathVariable Long documentId,
            @PathVariable Long version,
            @RequestBody DocumentReviewCreationDto reviewDto) {
        reviewService.review(projectId, documentId, version, reviewDto);
        return ResponseEntity.ok().build();
    }
}