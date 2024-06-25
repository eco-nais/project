package com.eco.environet.finance.services;

import com.eco.environet.finance.dto.RevenueDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RevenueService {
    RevenueDto create(RevenueDto newRevenue);
    RevenueDto findById(Long id);
    Page<RevenueDto> findAll(List<String> types, String startDate, String endDate, double amountAbove, double amountBellow, Pageable pageable);
    RevenueDto update(RevenueDto revenueDto);
    void delete(Long id);
}
