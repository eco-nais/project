package com.eco.environet.finance.dto;

import com.eco.environet.projects.dto.ProjectDto;
import com.eco.environet.users.dto.UserContactDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RevenueDto {
    @NotNull(message = "Id is required")
    private Long id;

    @NotNull(message = "Creation date is required")
    private Timestamp createdOn;

    @NotBlank(message = "Type is required")
    private String type;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be a positive number")
    private double amount;

    private UserContactDto donator;
    private ProjectDto project;
//    private Lecture lecture;
}
