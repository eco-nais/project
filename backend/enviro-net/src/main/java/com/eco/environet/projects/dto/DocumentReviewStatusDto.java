package com.eco.environet.projects.dto;

import lombok.Data;

@Data
public class DocumentReviewStatusDto {

    private Long version;
    private String status;
    private Boolean isReviewed;
}
