package com.eco.environet.projects.dto;

import com.eco.environet.projects.model.DocumentProgress;
import lombok.Data;

import java.util.List;

@Data
public class DocumentVersionsDto {

    private String projectName;
    private String documentName;
    private List<Long> versions;
    private DocumentProgress progress;
}
