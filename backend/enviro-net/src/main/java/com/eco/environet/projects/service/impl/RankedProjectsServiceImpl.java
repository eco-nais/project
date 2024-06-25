package com.eco.environet.projects.service.impl;

import com.eco.environet.finance.model.OrganizationGoal;
import com.eco.environet.finance.repository.OrganizationGoalRepository;
import com.eco.environet.projects.dto.RankedProjectDto;
import com.eco.environet.projects.model.Project;
import com.eco.environet.projects.model.Status;
import com.eco.environet.projects.model.Type;
import com.eco.environet.projects.repository.ProjectRepository;
import com.eco.environet.projects.service.RankedProjectsService;
import com.eco.environet.users.dto.UserContactDto;
import com.eco.environet.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Transactional
@Service
public class RankedProjectsServiceImpl implements RankedProjectsService {
    private final ProjectRepository projectRepository;
    private final OrganizationGoalRepository organizationGoalRepository;
    @Override
    public List<RankedProjectDto> findAllProjectRanks() {
        List<RankedProjectDto> result = new ArrayList<>();
        List<String> currentGoals = goalsString();

        // TODO - fetch all projects from LAST YEAR only
        List<Project> projects = projectRepository.findAll(Pageable.unpaged()).getContent();

        for (Project project : projects) {
            RankedProjectDto dto = new RankedProjectDto(project.getId(), project.getName(), project.getDescription(), project.getDurationMonths(), project.getBudget(), project.getType(), project.getStatus(), Mapper.map(project.getManager(), UserContactDto.class), project.getTags(), 5);
            dto = rankProject(dto, currentGoals);
            result.add(dto);
        }

        // Sort result by rank first, then by status
        Collections.sort(result, Comparator
                .comparingDouble(RankedProjectDto::getRank)
                // Custom comparator for status sorting
                .thenComparing((dto1, dto2) -> {
                    String status1 = dto1.getStatus().toString();
                    String status2 = dto2.getStatus().toString();
                    List<String> statusOrder = Arrays.asList("PENDING", "ONGOING", "APPROVED", "REJECTED", "ARCHIVED");
                    int index1 = statusOrder.indexOf(status1);
                    int index2 = statusOrder.indexOf(status2);
                    return Integer.compare(index1, index2);
                }));
        // Remove DRAFT projects from the result
        result.removeIf(dto -> dto.getStatus() == Status.DRAFT);

        return result;
    }
    private List<String> goalsString() {
        List<String> result = new ArrayList<>();
        List<OrganizationGoal> currentGoals = organizationGoalRepository.findByValidPeriodEndDateIsNull(Pageable.unpaged()).getContent();
        for (OrganizationGoal goal : currentGoals) {
            result.add(goal.getTitle());
        }
        return result;
    }
    private RankedProjectDto rankProject(RankedProjectDto dto, List<String> currentGoals) {
        if (dto.getType() == Type.EXTERNAL) {
            dto.setRank(1);
            return dto;
        } else {
            double rank = 5;
            for (String tag : dto.getTags()){
                if (currentGoals.contains(tag)) {
                    rank -= 0.5;
                }
            }
            dto.setRank(rank);
            return dto;
        }
    }
}
