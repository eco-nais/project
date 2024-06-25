package com.eco.environet.finance.model;

import com.eco.environet.projects.model.Project;
import com.eco.environet.users.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "projectBudgetBuilder")
@Table(name="project_budget", schema = "finance")
public class ProjectBudget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne
    private Project project;

    @ManyToOne
    private User creator;

    @Column(name = "total_revenues_amount", nullable = false)
    private double totalRevenuesAmount = 0;

    @Column(name = "total_expenses_amount", nullable = false)
    private double totalExpensesAmount = 0;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @Column(name = "version_log", columnDefinition = "TEXT")
    private String versionLog = "[]";

    @PreUpdate
    public void logVersionChange() {
        List<VersionLogEntry> logEntries = getVersionLogEntries();
        logEntries.add(new VersionLogEntry(this.version, this.totalRevenuesAmount, this.totalExpensesAmount));
        try {
            this.versionLog = new ObjectMapper().writeValueAsString(logEntries);
        } catch (JsonProcessingException e) {
            // TODO Handle JSON processing exception
        }
    }

    private List<VersionLogEntry> getVersionLogEntries() {
        try {
            return new ObjectMapper().readValue(
                    this.versionLog != null ? this.versionLog : "[]",
                    new TypeReference<List<VersionLogEntry>>() {}
            );
        } catch (JsonProcessingException e) {
            // TODO Handle JSON processing exception
            return new ArrayList<>();
        }
    }
}
