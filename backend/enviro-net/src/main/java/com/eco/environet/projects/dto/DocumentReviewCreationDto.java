package com.eco.environet.projects.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DocumentReviewCreationDto {

    @NotNull(message = "User is required")
    private Long userId;

    @NotNull(message = "Approval is required")
    private Boolean isApproved;

    private String comment;
}
