package com.galdino.ufood.infrastructure.repository.spec;

import com.galdino.ufood.domain.model.Restaurant;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;

public class RestaurantWithFreeDeliverySpec implements Specification<Restaurant> {
    @Override
    public Predicate toPredicate(Root<Restaurant> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder builder) {
        return builder.equal(root.get("deliveryFee"), BigDecimal.ZERO);
    }
}
