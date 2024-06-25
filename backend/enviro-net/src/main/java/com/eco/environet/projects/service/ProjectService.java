package com.eco.environet.projects.service;

import com.eco.environet.projects.dto.DocumentDto;
import com.eco.environet.projects.dto.ProjectDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProjectService {

    Page<ProjectDto> findAllProjects(String name, Pageable pageable);

    ProjectDto getProject(Long projectId);

    List<DocumentDto> getDocuments(Long projectId);
}
