package com.eco.environet.finance.repository;

import com.eco.environet.finance.model.ProjectDonation;
import org.springframework.data.jpa.domain.Specification;

public class ProjectDonationSpecifications {
    private ProjectDonationSpecifications() {}
    public static Specification<ProjectDonation> isForProject(Long projecId) {
        return (root, query, builder) ->
                builder.equal(root.get("project").get("id"), projecId);
    }
}
