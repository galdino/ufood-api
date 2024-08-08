package com.galdino.ufood.infrastructure.repository.spec;

import com.galdino.ufood.domain.filter.UOrderFilter;
import com.galdino.ufood.domain.model.UOrder;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;

public class UOrderSpecs {
    public static Specification<UOrder> useFilter(UOrderFilter filter) {
        return (root, criteriaQuery, builder) -> {
            if (UOrder.class.equals(criteriaQuery.getResultType())) {
                root.fetch("user");
                root.fetch("restaurant").fetch("kitchen");
            }

            var predicates = new ArrayList<>();

            if (filter.getUserId() != null) {
                predicates.add(builder.equal(root.get("user"), filter.getUserId()));
            }

            if (filter.getRestaurantId() != null) {
                predicates.add(builder.equal(root.get("restaurant"), filter.getRestaurantId()));
            }

            if (filter.getInitialRegisterDate() != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("registerDate"), filter.getInitialRegisterDate()));
            }

            if (filter.getFinalRegisterDate() != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("registerDate"), filter.getFinalRegisterDate()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
