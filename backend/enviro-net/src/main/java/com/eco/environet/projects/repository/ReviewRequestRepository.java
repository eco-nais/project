package com.eco.environet.projects.repository;

import com.eco.environet.projects.model.ReviewRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRequestRepository extends JpaRepository<ReviewRequest, Long> {

    @Query("SELECT req " +
            "FROM ReviewRequest req " +
            "WHERE NOT EXISTS (" +
            "   SELECT rev " +
            "   FROM Review rev " +
            "   WHERE rev.request = req AND rev.reviewer.id = :userId" +
            ") " +
            "AND EXISTS (" +
            "   SELECT ass " +
            "   FROM Assignment ass " +
            "   WHERE ass.userId = :userId " +
            "   AND ass.projectId = req.documentVersion.projectId " +
            "   AND ass.documentId = req.documentVersion.documentId" +
            "   AND ass.task = 'REVIEW'" +
            ")")
    List<ReviewRequest> findUnreviewedByReviewer(Long userId);

    @Query("SELECT r " +
            "FROM ReviewRequest r " +
            "WHERE r.documentVersion.projectId = :projectId" +
            " AND r.documentVersion.documentId = :documentId")
    List<ReviewRequest> findAllByDocument(Long projectId, Long documentId);

    @Query("SELECT r " +
            "FROM ReviewRequest r " +
            "WHERE r.documentVersion.projectId = :projectId AND r.documentVersion.documentId = :documentId " +
            "AND r.documentVersion.version = :version")
    Optional<ReviewRequest> findByDocumentVersion(Long projectId, Long documentId, Long version);
}
