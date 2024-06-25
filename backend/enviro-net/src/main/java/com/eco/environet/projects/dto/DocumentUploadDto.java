package com.eco.environet.projects.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class DocumentUploadDto {

    @NotNull(message = "File is required")
    private MultipartFile file;

    @NotNull(message = "Author is required")
    private Long userId;

    private Double progress;
}
