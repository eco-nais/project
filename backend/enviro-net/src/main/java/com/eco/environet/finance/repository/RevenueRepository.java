package com.eco.environet.finance.repository;

import com.eco.environet.finance.model.Revenue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RevenueRepository extends JpaRepository<Revenue, Long> {
    Page<Revenue> findAll(Specification<Revenue> spec, Pageable pageable);;
}
