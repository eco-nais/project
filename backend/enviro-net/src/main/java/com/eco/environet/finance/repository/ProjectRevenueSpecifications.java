package com.eco.environet.finance.repository;

import com.eco.environet.finance.model.ProjectRevenue;
import org.springframework.data.jpa.domain.Specification;

public class ProjectRevenueSpecifications {
    private ProjectRevenueSpecifications() {}
    public static Specification<ProjectRevenue> isForProject(Long projecId) {
        return (root, query, builder) ->
                builder.equal(root.get("project").get("id"), projecId);
    }
}
