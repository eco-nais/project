package com.eco.environet.finance.services;

import com.eco.environet.finance.dto.ProjectBudgetDto;

public interface ProjectBudgetService {
    ProjectBudgetDto create(ProjectBudgetDto newProjectBudget);
    ProjectBudgetDto findByProjectId(Long id);
    //Page<ProjectBudgetDto> findAll(List<Long> projectIds, Pageable pageable);
    ProjectBudgetDto update(ProjectBudgetDto projectBudgetDto);
}
