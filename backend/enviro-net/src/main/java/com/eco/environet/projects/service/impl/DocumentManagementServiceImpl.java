package com.eco.environet.projects.service.impl;

import com.eco.environet.projects.dto.*;
import com.eco.environet.projects.model.*;
import com.eco.environet.projects.model.id.DocumentId;
import com.eco.environet.projects.model.id.DocumentVersionId;
import com.eco.environet.projects.repository.AssignmentRepository;
import com.eco.environet.projects.repository.DocumentRepository;
import com.eco.environet.projects.repository.DocumentVersionRepository;
import com.eco.environet.projects.repository.ProjectRepository;
import com.eco.environet.projects.service.DocumentManagementService;
import com.eco.environet.users.model.User;
import com.eco.environet.users.repository.UserRepository;
import com.eco.environet.util.Mapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class DocumentManagementServiceImpl implements DocumentManagementService {

    @Value("${projectFilePath}")
    private String projectFilePath;
    private final ProjectRepository projectRepository;
    private final DocumentRepository documentRepository;
    private final DocumentVersionRepository versionRepository;
    private final AssignmentRepository assignmentRepository;
    private final UserRepository userRepository;

    public DocumentDto createDocument(Long projectId, DocumentCreationDto documentDto) throws IOException {
        if (projectId == null || documentDto == null || documentDto.getFile() == null || documentDto.getFile().isEmpty()) {
            throw new IllegalArgumentException("Invalid parameters for document upload");
        }

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));

        Path filePath = saveFile(documentDto.getName(), documentDto.getFile(), project.getName(), 0L);
        Document savedDocument = createDocument(projectId, documentDto.getName());
        createDocumentVersion(filePath, savedDocument, project.getManager());

        return Mapper.map(savedDocument, DocumentDto.class);
    }

    @Override
    public DocumentDto uploadDocument(Long projectId, Long documentId, DocumentUploadDto documentDto) throws IOException {
        if (projectId == null || documentId == null || documentDto == null || documentDto.getFile() == null || documentDto.getFile().isEmpty()) {
            throw new IllegalArgumentException("Invalid parameters for document upload");
        }

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));

        Document document = documentRepository.findByDocumentIdAndProjectId(documentId, projectId)
                .orElseThrow(() -> new EntityNotFoundException("Document not found"));

        User author = userRepository.findById(documentDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (!assignmentRepository.isWriter(documentId, projectId, documentDto.getUserId())) {
            throw new IllegalArgumentException("Uploader isn't assigned to document");
        }

        if (documentDto.getProgress() != null && documentDto.getProgress() != 0.0) {
            updateProgress(documentDto, document);
        }

        Path filePath = saveFile(document.getName(), documentDto.getFile(), project.getName(), versionRepository.getNextVersion(projectId, documentId));
        createDocumentVersion(filePath, document, author);

        return Mapper.map(document, DocumentDto.class);
    }

    @Override
    public void deleteDocument(Long projectId, Long documentId) {
        DocumentId id = new DocumentId(projectId, documentId);

        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Document not found"));

        List<DocumentVersion> documentVersions = versionRepository.findByDocumentIdAndProjectId(documentId, projectId);
        for (DocumentVersion version : documentVersions) {
            deleteDocumentVersionFromFileSystem(version.getFilePath());
        }

        versionRepository.deleteAll(documentVersions);
        documentRepository.delete(document);
    }

    @Override
    public DocumentVersionsDto getDocumentVersions(Long projectId, Long documentId) {
        if (projectId == null || documentId == null) {
            throw new IllegalArgumentException("Invalid parameters for document upload");
        }

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));

        Document document = documentRepository.findByDocumentIdAndProjectId(documentId, projectId)
                .orElseThrow(() -> new EntityNotFoundException("Document not found"));

        List<Long> versions = versionRepository.findByDocumentIdAndProjectId(documentId, projectId)
                .stream().map(DocumentVersion::getVersion)
                .toList();

        return mapDocumentVersionsDto(project, document, versions);
    }

    @Override
    public List<DocumentTaskDto> getAssignedDocuments(Long userId) {
        List<Assignment> assignments = assignmentRepository.findActiveByUser(userId);

        return assignments.stream()
                .map(assignment -> {
                    DocumentId id = new DocumentId(assignment.getProjectId(), assignment.getDocumentId());

                    Project project = projectRepository.findById(id.getProjectId())
                            .orElseThrow(() -> new EntityNotFoundException("Project not found"));

                    Document document = documentRepository.findById(id)
                            .orElseThrow(() -> new EntityNotFoundException("Document not found"));

                    return mapDocumentTaskDto(assignment, project, document);
                }).toList();
    }

    public Resource getDocumentFile(Long projectId, Long documentId, Long version) throws IOException {
        DocumentVersionId id = new DocumentVersionId(projectId, documentId, version);
        DocumentVersion documentVersion = versionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Document version not found"));

        Path filePath = Paths.get(documentVersion.getFilePath());
        Resource resource = new UrlResource(filePath.toUri());

        return Optional.of(resource)
                .orElseThrow(() -> new FileNotFoundException("File not found " + documentVersion.getFilePath()));
    }

    @Override
    public Task getAssignment(Long projectId, Long documentId, Long userId) {
        return assignmentRepository.findByDocumentAndUser(documentId, projectId, userId);
    }

    private void updateProgress(DocumentUploadDto documentDto, Document document) {
        document.updateProgress(documentDto.getProgress());
        documentRepository.save(document);
    }

    private Document createDocument(Long projectId, String documentName) {
        Document document = new Document();
        document.setProjectId(projectId);
        document.setName(documentName);
        document.setProgress(new DocumentProgress());
        return documentRepository.save(document);
    }

    private void createDocumentVersion(Path filePath, Document savedDocument, User author) {
        DocumentVersion documentVersion = new DocumentVersion();
        documentVersion.setDocumentId(savedDocument.getDocumentId());
        documentVersion.setProjectId(savedDocument.getProjectId());
        documentVersion.setFilePath(filePath.toString());
        documentVersion.setAuthor(author);
        versionRepository.save(documentVersion);
    }

    private DocumentVersionsDto mapDocumentVersionsDto(Project project, Document document, List<Long> versions) {
        DocumentVersionsDto dto = new DocumentVersionsDto();
        dto.setProjectName(project.getName());
        dto.setDocumentName(document.getName());
        dto.setVersions(versions);
        dto.setProgress(document.getProgress());
        return dto;
    }

    private DocumentTaskDto mapDocumentTaskDto(Assignment assignment, Project project, Document document) {
        DocumentTaskDto dto = new DocumentTaskDto();
        dto.setDocumentId(document.getDocumentId());
        dto.setProjectId(document.getProjectId());
        dto.setProjectName(project.getName());
        dto.setDocumentName(document.getName());
        dto.setProgress(document.getProgress());
        dto.setTask(assignment.getTask());
        return dto;
    }

    private Path saveFile(String documentName, MultipartFile file, String projectName, Long version) throws IOException {
        String originalFilename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String extension = StringUtils.getFilenameExtension(originalFilename);
        String filename = documentName.replaceAll("\\s+", "_").toLowerCase() + "_v" + version + "." + extension;

        Path projectFolder = createProjectFolder(projectName);

        Path filePath = Paths.get(projectFolder.toString(), filename);
        Files.copy(file.getInputStream(), filePath);
        return filePath;
    }

    private Path createProjectFolder(String projectName) throws IOException {
        Path projectFolder = Paths.get(projectFilePath + File.separator + projectName);
        if (!Files.exists(projectFolder)) {
            Files.createDirectories(projectFolder);
        }
        return projectFolder;
    }

    private void deleteDocumentVersionFromFileSystem(String filePath) {
        try {
            Path path = Paths.get(filePath);
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
