package com.eco.environet.projects.model;

import com.eco.environet.users.model.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "reviews", schema = "projects")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(name = "review_date", nullable = false)
    private Timestamp reviewDate;

    @ManyToOne
    @JoinColumn(name = "request_id", referencedColumnName = "id")
    private ReviewRequest request;

    @ManyToOne
    @JoinColumn(name = "reviewer_id", referencedColumnName = "id")
    private User reviewer;

    @Column(length = 500)
    private String comment;

    @Column(name = "is_approved", nullable = false)
    private Boolean isApproved;

    public Review(ReviewRequest request, User reviewer, String comment, Boolean isApproved) {
        this.request = request;
        this.reviewDate = Timestamp.valueOf(LocalDateTime.now());
        this.reviewer = reviewer;
        this.comment = comment;
        this.isApproved = isApproved;
    }
}
