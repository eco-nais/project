package com.eco.environet.projects.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class DocumentReviewDto {

    private Long version;
    private Timestamp reviewDate;
    private TeamMemberDto reviewer;
    private String comment;
    private Boolean isApproved;
}
