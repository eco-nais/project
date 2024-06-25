package com.eco.environet.projects.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class DocumentCreationDto {

    @NotNull(message = "File is required")
    private MultipartFile file;

    @NotBlank(message = "Name is required")
    private String name;
}
