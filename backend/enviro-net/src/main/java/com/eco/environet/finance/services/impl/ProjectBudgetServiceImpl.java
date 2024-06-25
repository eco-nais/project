package com.eco.environet.finance.services.impl;

import com.eco.environet.finance.dto.ProjectBudgetDto;
import com.eco.environet.finance.model.ProjectBudget;
import com.eco.environet.finance.repository.ProjectBudgetRepository;
import com.eco.environet.finance.services.ProjectBudgetService;
import com.eco.environet.projects.model.Project;
import com.eco.environet.users.model.User;
import com.eco.environet.util.Mapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectBudgetServiceImpl implements ProjectBudgetService {
    @Value("${baseFrontUrl}")
    private String baseFrontUrl;
    private final ProjectBudgetRepository repository;

    @Override
    public ProjectBudgetDto create(ProjectBudgetDto newProjectBudget) {

        ProjectBudget projectBudget = ProjectBudget.projectBudgetBuilder()
                .project(Mapper.map(newProjectBudget.getProject(), Project.class))
                .creator(Mapper.map(newProjectBudget.getCreator(), User.class))
                .totalRevenuesAmount(newProjectBudget.getTotalRevenuesAmount())
                .totalExpensesAmount(newProjectBudget.getTotalExpensesAmount())
                .build();
        repository.save(projectBudget);
        return Mapper.map(projectBudget, ProjectBudgetDto.class);
    }

    @Override
    public ProjectBudgetDto findByProjectId(Long id) {
//        Project project = projectRepository.findById(id)
//              .orElseThrow(()-> new EntityNotFoundException("Project not found with ID: " + id));
        ProjectBudget projectBudget = repository.findByProjectId(id)
                .orElseThrow(()-> new EntityNotFoundException("Project budget not found for project with ID: " + id));
        return Mapper.map(projectBudget, ProjectBudgetDto.class);
    }

    @Override
    public ProjectBudgetDto update(ProjectBudgetDto projectBudgetDto) {
        ProjectBudget projectBudget = repository.findById(projectBudgetDto.getId())
                .orElseThrow(()-> new EntityNotFoundException("Project budget not found with ID: " + projectBudgetDto.getId()));

        if (validUpdate(projectBudget, projectBudgetDto)) {
            projectBudget.setTotalExpensesAmount(projectBudgetDto.getTotalExpensesAmount());
            projectBudget.setTotalRevenuesAmount(projectBudgetDto.getTotalRevenuesAmount());
            repository.save(projectBudget);
            return Mapper.map(projectBudget, ProjectBudgetDto.class);
        } else {
            String errorMessage = "Invalid update for project budget: \n" +
                    "\nTotalExpensesAmount: \tdifference: " + (projectBudget.getTotalExpensesAmount() - projectBudgetDto.getTotalExpensesAmount()) +
                    "\n\tfrom " + projectBudget.getTotalExpensesAmount() + " to:" + projectBudgetDto.getTotalExpensesAmount() +
                    "\nTotalRevenuesAmount: difference: " + (projectBudget.getTotalRevenuesAmount() - projectBudgetDto.getTotalRevenuesAmount()) +
                    "\n\tfrom " + projectBudget.getTotalRevenuesAmount() + " to:" + projectBudgetDto.getTotalRevenuesAmount() +
                    "\n\nDifferences don't match!";
            throw new IllegalArgumentException(errorMessage);
        }
    }

    private boolean validUpdate(ProjectBudget projectBudget, ProjectBudgetDto projectBudgetDto) {
        boolean valid = false;
        // new revenue scenario : TotalExpensesAmount stays the same, but TotalRevenuesAmount updates
        if (projectBudget.getTotalExpensesAmount() == projectBudgetDto.getTotalExpensesAmount()
                && projectBudget.getTotalRevenuesAmount() != projectBudgetDto.getTotalRevenuesAmount()) {
            valid = true;
        }
        // new expense scenario : TotalExpensesAmount updates, TotalRevenuesAmount updates
        else if (projectBudget.getTotalExpensesAmount() != projectBudgetDto.getTotalExpensesAmount()
                && projectBudget.getTotalRevenuesAmount() != projectBudgetDto.getTotalRevenuesAmount()) {
            double expensesDiff = projectBudgetDto.getTotalExpensesAmount() - projectBudget.getTotalExpensesAmount();
            double revenuesDiff = projectBudgetDto.getTotalRevenuesAmount() - projectBudget.getTotalRevenuesAmount();
            if (expensesDiff == -revenuesDiff) {
                valid = true;
            }
        }

        return valid;
    }
}
