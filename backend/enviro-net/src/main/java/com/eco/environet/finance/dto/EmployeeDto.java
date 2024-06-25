package com.eco.environet.finance.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    @NotNull(message = "Id is required")
    private Long id;

    @NotNull(message = "Username is required")
    private String username;

    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "Surname is required")
    private String surname;

    @NotNull(message = "Email is required")
    private String email;

    @NotNull(message = "Wage is required")
    @Positive(message = "Wage must be a positive number")
    private double wage;

    private double workingHours;
    private double overtimeWage;

    @Override
    public String toString() {
        return name + " " + surname + " (" + username + ")";
    }
}
