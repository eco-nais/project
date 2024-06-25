package com.eco.environet.finance.repository;

import com.eco.environet.finance.model.ProjectBudget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectBudgetRepository extends JpaRepository<ProjectBudget, Long> {
    @Query("SELECT p FROM ProjectBudget p WHERE p.project.id = :id")
    Optional<ProjectBudget> findByProjectId(@Param("id") Long id);
}
