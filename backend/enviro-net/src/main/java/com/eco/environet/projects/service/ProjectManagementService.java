package com.eco.environet.projects.service;

import com.eco.environet.projects.dto.ProjectCreationDto;
import com.eco.environet.projects.dto.ProjectDto;
import com.eco.environet.projects.dto.ProjectUpdateDto;
import org.springframework.stereotype.Service;

@Service
public interface ProjectManagementService {

    ProjectDto create(ProjectCreationDto projectDto);

    ProjectDto get(Long projectId);

    ProjectDto update(Long projectId, ProjectUpdateDto updateDto);

    void delete(Long projectId);
}
