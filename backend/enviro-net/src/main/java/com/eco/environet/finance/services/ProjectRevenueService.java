package com.eco.environet.finance.services;

import com.eco.environet.finance.dto.TotalProjectRevenueDto;
import org.springframework.data.domain.Pageable;

public interface ProjectRevenueService {
    TotalProjectRevenueDto findAllProjectDonations(Long projectId, Pageable pageable);
    TotalProjectRevenueDto findAllProjectRevenue(Long projectId, Pageable pageable);
}
