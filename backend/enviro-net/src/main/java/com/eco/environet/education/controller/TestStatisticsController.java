package com.eco.environet.education.controller;

import com.eco.environet.education.dto.QuestionStatistics;
import com.eco.environet.education.services.TestStatisticsService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/test-execution/statistics")
@Tag(name = "Test statistics", description = "Get statistics for a test")
public class TestStatisticsController {

    private final TestStatisticsService service;

    @Operation(summary = "Fetch top 3 questions for test by criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = QuestionStatistics.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content)
    })
    @GetMapping("/top3")
    @PreAuthorize("hasRole('EDUCATOR')")
    public ResponseEntity<List<QuestionStatistics>> findTop3ByLectureIdAndCriteria(@RequestParam Long lectureId, @RequestParam String criteria) {
        var result = service.findTop3ByLectureIdAndCriteria(lectureId, criteria);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
