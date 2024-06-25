package com.eco.environet.projects.service.impl;

import com.eco.environet.projects.dto.DocumentReviewCreationDto;
import com.eco.environet.projects.dto.DocumentReviewDto;
import com.eco.environet.projects.dto.DocumentReviewStatusDto;
import com.eco.environet.projects.dto.DocumentTaskDto;
import com.eco.environet.projects.model.*;
import com.eco.environet.projects.model.id.DocumentVersionId;
import com.eco.environet.projects.repository.*;
import com.eco.environet.projects.service.ReviewService;
import com.eco.environet.users.model.User;
import com.eco.environet.users.repository.UserRepository;
import com.eco.environet.util.Mapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Transactional
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRequestRepository requestRepository;
    private final ReviewRepository reviewRepository;
    private final DocumentVersionRepository versionRepository;
    private final DocumentRepository documentRepository;
    private final AssignmentRepository assignmentRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Override
    public void requestReview(Long projectId, Long documentId, Long version) {
        DocumentVersionId id = new DocumentVersionId(projectId, documentId, version);
        DocumentVersion documentVersion = versionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Document version not found"));

        ReviewRequest request = new ReviewRequest(documentVersion);
        requestRepository.save(request);
    }

    @Override
    public List<DocumentReviewDto> getDocumentReviews(Long projectId, Long documentId) {
        List<Review> reviews = reviewRepository.findAllByDocument(projectId, documentId);
        List<DocumentReviewDto> reviewDtos = Mapper.mapList(reviews, DocumentReviewDto.class);

        IntStream.range(0, reviews.size())
                .forEach(i -> {
                    reviewDtos.get(i).setVersion(reviews.get(i).getRequest().getDocumentVersion().getVersion());
                    reviewDtos.get(i).getReviewer().setFirstName(reviews.get(i).getReviewer().getName());
                    reviewDtos.get(i).getReviewer().setLastName(reviews.get(i).getReviewer().getSurname());
                });

        return reviewDtos;
    }

    @Override
    public List<DocumentReviewStatusDto> getDocumentReviewStatuses(Long projectId, Long documentId) {
        List<ReviewRequest> requests = requestRepository.findAllByDocument(projectId, documentId);
        List<DocumentReviewStatusDto> requestDtos = Mapper.mapList(requests, DocumentReviewStatusDto.class);

        IntStream.range(0, requests.size())
                .forEach(i ->
                        requestDtos.get(i).setVersion(requests.get(i).getDocumentVersion().getVersion())
                );

        return requestDtos;
    }

    public List<DocumentTaskDto> getUnreviewedDocuments(Long userId) {
        List<ReviewRequest> unreviewedRequests = requestRepository.findUnreviewedByReviewer(userId);

        return unreviewedRequests.stream()
                .map(this::createDocumentTask).toList();
    }

    @Override
    public void review(Long projectId, Long documentId, Long version, DocumentReviewCreationDto reviewDto) {
        ReviewRequest request = requestRepository.findByDocumentVersion(projectId, documentId, version)
                .orElseThrow(() -> new EntityNotFoundException("Review request not found"));

        User reviewer = userRepository.findById(reviewDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        List<Review> reviews = reviewRepository.findByRequestId(request.getId());
        validateReview(reviewer, reviews);

        Review newReview = new Review(request, reviewer, reviewDto.getComment(), reviewDto.getIsApproved());
        reviewRepository.save(newReview);
        reviews.add(newReview);

        updateRequestReviewStatus(projectId, documentId, request, reviews);
    }

    private void updateRequestReviewStatus(Long projectId, Long documentId, ReviewRequest request, List<Review> reviews) {
        List<Assignment> reviewAssignments = assignmentRepository.findByDocumentAndTask(documentId, projectId, Task.REVIEW);
        if (reviewAssignments.size() == reviews.size()) {
            boolean approved = reviews.stream()
                    .allMatch(Review::getIsApproved);
            ReviewStatus status = approved ? ReviewStatus.APPROVED : ReviewStatus.REJECTED;
            request.setStatus(status);
            request.setIsReviewed(true);
        } else if (request.getStatus() != ReviewStatus.IN_PROGRESS) {
            request.setStatus(ReviewStatus.IN_PROGRESS);
        }
    }

    private void validateReview(User reviewer, List<Review> reviews) {
        boolean reviewExists = reviews.stream()
                .anyMatch(review -> review.getReviewer().getId().equals(reviewer.getId()));
        if (reviewExists) {
            throw new IllegalStateException("Review already exists for this version by this user");
        }
    }

    private DocumentTaskDto createDocumentTask(ReviewRequest request) {
        DocumentTaskDto documentDto = new DocumentTaskDto();
        Long projectId = request.getDocumentVersion().getProjectId();
        Long documentId = request.getDocumentVersion().getDocumentId();
        documentDto.setProjectId(projectId);
        documentDto.setDocumentId(documentId);
        documentDto.setVersion(request.getDocumentVersion().getVersion());

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));

        Document document = documentRepository.findByDocumentIdAndProjectId(documentId, projectId)

                .orElseThrow(() -> new EntityNotFoundException("Document not found"));
        documentDto.setProjectName(project.getName());
        documentDto.setDocumentName(document.getName());
        documentDto.setProgress(document.getProgress());
        documentDto.setTask(Task.REVIEW);

        return documentDto;
    }
}
