package com.eco.environet.finance.services.impl;

import com.eco.environet.finance.dto.RevenueDto;
import com.eco.environet.finance.dto.TotalProjectRevenueDto;
import com.eco.environet.finance.model.ProjectDonation;
import com.eco.environet.finance.repository.ProjectDonationRepository;
import com.eco.environet.finance.repository.ProjectDonationSpecifications;
import com.eco.environet.finance.model.ProjectRevenue;
import com.eco.environet.finance.repository.ProjectRevenueRepository;
import com.eco.environet.finance.repository.ProjectRevenueSpecifications;
import com.eco.environet.finance.services.ProjectRevenueService;
import com.eco.environet.projects.model.Project;
import com.eco.environet.projects.model.Type;
import com.eco.environet.projects.repository.ProjectRepository;
import com.eco.environet.users.security.auth.JwtService;
import com.eco.environet.util.Mapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectRevenueServiceImpl implements ProjectRevenueService {
    @Value("${baseFrontUrl}")
    private String baseFrontUrl;
    private final JwtService jwtService;
    private final ProjectRepository projectRepository;
    private final ProjectRevenueRepository projectRevenueRepository;
    private final ProjectDonationRepository projectDonationRepository;

    @Override
    public TotalProjectRevenueDto findAllProjectDonations(Long projectId, Pageable pageable) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with ID: " + projectId));
        Specification<ProjectDonation> spec =
                Specification.where(projectId == null ? null
                        : ProjectDonationSpecifications.isForProject(projectId));
        Page<ProjectDonation> all = projectDonationRepository.findAll(spec, pageable);
        Page<RevenueDto> allDtos = Mapper.mapPage(all, RevenueDto.class);
        return new TotalProjectRevenueDto(allDtos);
    }

    @Override
    public TotalProjectRevenueDto findAllProjectRevenue(Long projectId, Pageable pageable) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with ID: " + projectId));
        if (project.getType()!= Type.EXTERNAL){
            throw new IllegalArgumentException("Invalid project provided, project  type must be EXTERNAL: " + project);
        }
        Specification<ProjectRevenue> specExternalRevenue =
                Specification.where(projectId == null ? null
                        : ProjectRevenueSpecifications.isForProject(projectId));
        List<ProjectRevenue> allExternalRevenue = projectRevenueRepository.findAll(specExternalRevenue, pageable).getContent();
        Specification<ProjectDonation> specDonations =
                Specification.where(projectId == null ? null
                        : ProjectDonationSpecifications.isForProject(projectId));
        List<ProjectDonation> allDonations = projectDonationRepository.findAll(specDonations, pageable).getContent();

        // Combine both lists
        List<Object> combinedList = new ArrayList<>();
        combinedList.addAll(allExternalRevenue);
        combinedList.addAll(allDonations);

        // Map combined list to RevenueDto
        List<RevenueDto> allDtos = combinedList.stream()
                .map(item -> {
                    if (item instanceof ProjectRevenue) {
                        return Mapper.map((ProjectRevenue) item, RevenueDto.class);
                    } else if (item instanceof ProjectDonation) {
                        return Mapper.map((ProjectDonation) item, RevenueDto.class);
                    } else {
                        return null; // or throw an exception
                    }
                })
                .collect(Collectors.toList());

        // Paginate the combined list
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), allDtos.size());
        Page<RevenueDto> page = new PageImpl<>(allDtos.subList(start, end), pageable, allDtos.size());

        // Create and return TotalProjectRevenueDto
        return new TotalProjectRevenueDto(page);
    }
}
