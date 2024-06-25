package com.eco.environet.projects.dto;

import lombok.Data;

@Data
public class TeamMemberDto {

    private Long projectId;
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
}
