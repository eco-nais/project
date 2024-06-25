package com.eco.environet.projects.repository;

import com.eco.environet.projects.model.Project;
import org.springframework.data.jpa.domain.Specification;

public class ProjectSpecifications {

    private ProjectSpecifications() {
    }

    public static Specification<Project> nameLike(String name) {
        return (root, query, builder) ->
                builder.like(builder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }
}
