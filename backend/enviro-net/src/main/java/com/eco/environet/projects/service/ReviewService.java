package com.eco.environet.projects.service;

import com.eco.environet.projects.dto.DocumentReviewCreationDto;
import com.eco.environet.projects.dto.DocumentReviewDto;
import com.eco.environet.projects.dto.DocumentReviewStatusDto;
import com.eco.environet.projects.dto.DocumentTaskDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewService {

    void requestReview(Long projectId, Long documentId, Long version);
    List<DocumentReviewDto> getDocumentReviews(Long projectId, Long documentId);
    List<DocumentReviewStatusDto> getDocumentReviewStatuses(Long projectId, Long documentId);
    List<DocumentTaskDto> getUnreviewedDocuments(Long userId);
    void review(Long projectId, Long documentId, Long version, DocumentReviewCreationDto reviewDto);
}
