package com.eco.environet.users.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {
    @NotNull(message = "Id is required")
    private Long id;

    @NotNull(message = "Creation date is required")
    private Timestamp createdOn;

    @NotBlank(message = "User is required")
    private UserContactDto user;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Creation date is required")
    private boolean isRead;

    private String link;
}
