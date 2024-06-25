package com.eco.environet.finance.repository;

import com.eco.environet.finance.model.Revenue;
import com.eco.environet.finance.model.RevenueType;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Timestamp;
import java.util.List;

public class RevenueSpecifications {
    private RevenueSpecifications() {}
    public static Specification<Revenue> typeIn(List<RevenueType> typeList){
        return ((root, query, criteriaBuilder) -> root.get("type").in(typeList));
    }
    public static Specification<Revenue> afterDate(Timestamp startDate) {
        return (root, query, criteriaBuilder) -> {
            if (startDate != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("createdOn"), startDate);
            } else {
                return criteriaBuilder.conjunction();
            }
        };
    }
    public static Specification<Revenue> beforeDate(Timestamp endDate) {
        return (root, query, criteriaBuilder) -> {
            if (endDate != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("createdOn"), endDate);
            } else {
                return criteriaBuilder.conjunction();
            }
        };
    }

    public static Specification<Revenue> amountAbove(double amountValue) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("amount"), amountValue);
    }
    public static Specification<Revenue> amountBelow(double amountValue) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("amount"), amountValue);
    }
}
