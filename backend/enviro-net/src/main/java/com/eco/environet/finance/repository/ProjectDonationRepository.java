package com.eco.environet.finance.repository;

import com.eco.environet.finance.model.ProjectDonation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectDonationRepository extends JpaRepository<ProjectDonation, Long> {
    Page<ProjectDonation> findAll(Specification<ProjectDonation> spec, Pageable pageable);;
}
