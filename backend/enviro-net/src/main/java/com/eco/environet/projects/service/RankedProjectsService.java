package com.eco.environet.projects.service;

import com.eco.environet.projects.dto.RankedProjectDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RankedProjectsService {
    List<RankedProjectDto> findAllProjectRanks();
}
