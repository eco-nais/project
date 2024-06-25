package com.eco.environet.projects.dto;

import com.eco.environet.projects.model.DocumentProgress;
import com.eco.environet.projects.model.Task;
import lombok.Data;

import java.util.List;

@Data
public class DocumentTaskDto {

    private Long documentId;
    private Long projectId;
    private Long version;
    private String projectName;
    private String documentName;
    private DocumentProgress progress;
    private Task task;
}
