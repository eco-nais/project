package com.eco.environet.projects.repository;

import com.eco.environet.projects.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r " +
            "FROM Review r " +
            "WHERE r.request.documentVersion.projectId = :projectId " +
            "AND r.request.documentVersion.documentId = :documentId")
    List<Review> findAllByDocument(Long projectId, Long documentId);

    List<Review> findByRequestId(Long requestId);
}
