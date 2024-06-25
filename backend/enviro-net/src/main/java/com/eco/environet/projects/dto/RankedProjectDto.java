package com.eco.environet.projects.dto;

import com.eco.environet.projects.model.Status;
import com.eco.environet.projects.model.Type;
import com.eco.environet.users.dto.UserContactDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RankedProjectDto {
    private Long id;
    private String name;
    private String description;
    private int durationMonths;
    private double budget;
    private Type type;
    private Status status;
    private UserContactDto manager;
    private List<String> tags;
    private double rank;
}
