package com.eco.environet.projects.service.impl;

import com.eco.environet.projects.dto.*;
import com.eco.environet.projects.model.*;
import com.eco.environet.projects.repository.*;
import com.eco.environet.projects.service.ProjectService;
import com.eco.environet.users.model.User;
import com.eco.environet.users.repository.UserRepository;
import com.eco.environet.util.Mapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final DocumentRepository documentRepository;
    private final AssignmentRepository assignmentRepository;
    private final UserRepository userRepository;

    @Override
    public Page<ProjectDto> findAllProjects(String name, Pageable pageable) {
        Specification<Project> spec = Specification.where(org.apache.commons.lang3.StringUtils.isBlank(name) ?
                null : ProjectSpecifications.nameLike(name));
        Page<Project> projects = projectRepository.findAll(spec, pageable);

        return Mapper.mapPage(projects, ProjectDto.class);
    }

    @Override
    public ProjectDto getProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));

        return Mapper.map(project, ProjectDto.class);
    }

    @Override
    public List<DocumentDto> getDocuments(Long projectId) {
        List<Document> documents = documentRepository.findByProjectId(projectId);
        List<DocumentDto> documentDtos = Mapper.mapList(documents, DocumentDto.class);

        for (DocumentDto documentDto : documentDtos) {
            List<Assignment> assignments = assignmentRepository.findByDocument(documentDto.getDocumentId(), projectId);

            List<TeamMemberDto> writers = new ArrayList<>();
            List<TeamMemberDto> reviewers = new ArrayList<>();

            for (Assignment assignment : assignments) {
                User user = userRepository.findById(assignment.getUserId())
                        .orElseThrow(() -> new EntityNotFoundException("User not found"));
                TeamMemberDto teamMemberDto = new TeamMemberDto();
                teamMemberDto.setUserId(user.getId());
                teamMemberDto.setFirstName(user.getName());
                teamMemberDto.setLastName(user.getSurname());

                if (assignment.getTask() == Task.WRITE) {
                    writers.add(teamMemberDto);
                } else if (assignment.getTask() == Task.REVIEW) {
                    reviewers.add(teamMemberDto);
                }
            }

            documentDto.setWriters(writers);
            documentDto.setReviewers(reviewers);}
        return documentDtos;
    }
}
