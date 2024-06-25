package com.eco.environet.finance.dto;

import com.eco.environet.projects.dto.ProjectDto;
import com.eco.environet.users.dto.UserContactDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectBudgetDto {
    @NotNull(message = "Id is required")
    private Long id;

    private ProjectDto project;
    private UserContactDto creator;

    @NotNull(message = "Total Revenues Amount is required")
    @PositiveOrZero(message = "Total Revenues Amount must be a positive number")
    private double totalRevenuesAmount;

    @NotNull(message = "Total Expenses Amount is required")
    @PositiveOrZero(message = "Total Expenses Amount must be a positive number")
    private double totalExpensesAmount;
}
