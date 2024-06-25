package com.eco.environet.projects.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "review_requests", schema = "projects")
public class ReviewRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(name = "request_date", nullable = false)
    private Timestamp requestDate;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "version", referencedColumnName = "version"),
            @JoinColumn(name = "document_id", referencedColumnName = "document_id"),
            @JoinColumn(name = "project_id", referencedColumnName = "project_id")
    })
    private DocumentVersion documentVersion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReviewStatus status;

    @Column(name = "is_reviewed", nullable = false)
    private Boolean isReviewed;

    public ReviewRequest(DocumentVersion documentVersion) {
        this.documentVersion = documentVersion;
        this.requestDate = Timestamp.valueOf(LocalDateTime.now());
        this.status = ReviewStatus.REQUESTED;
        this.isReviewed = false;
    }
}
