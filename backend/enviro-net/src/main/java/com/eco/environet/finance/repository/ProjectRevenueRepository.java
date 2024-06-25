package com.eco.environet.finance.repository;

import com.eco.environet.finance.model.ProjectRevenue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRevenueRepository extends JpaRepository<ProjectRevenue, Long> {
    Page<ProjectRevenue> findAll(Specification<ProjectRevenue> spec, Pageable pageable);;
}
