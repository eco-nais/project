package com.eco.environet.projects.service;

import com.eco.environet.projects.dto.*;
import com.eco.environet.projects.model.Task;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface DocumentManagementService {

    DocumentDto createDocument(Long projectId, DocumentCreationDto documentDto) throws IOException;
    DocumentDto uploadDocument(Long projectId, Long documentId, DocumentUploadDto documentDto) throws IOException;
    void deleteDocument(Long projectId, Long documentId);
    DocumentVersionsDto getDocumentVersions(Long projectId, Long documentId);
    List<DocumentTaskDto> getAssignedDocuments(Long userId);
    Resource getDocumentFile(Long projectId, Long documentId, Long version) throws IOException;
    Task getAssignment(Long projectId, Long documentId, Long userId);
}
