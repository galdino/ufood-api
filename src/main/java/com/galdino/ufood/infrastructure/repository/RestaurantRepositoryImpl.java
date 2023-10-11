package com.galdino.ufood.infrastructure.repository;

import com.galdino.ufood.domain.model.Restaurant;
import com.galdino.ufood.domain.repository.RestaurantRepositoryQueries;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.List;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepositoryQueries {

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<Restaurant> find(String name, BigDecimal initialFee, BigDecimal finalFee) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Restaurant> criteria = builder.createQuery(Restaurant.class);
        Root<Restaurant> root = criteria.from(Restaurant.class);

        Predicate namePredicate = builder.like(root.get("name"), "%" + name + "%");
        Predicate initialFeePredicate = builder.greaterThanOrEqualTo(root.get("deliveryFee"), initialFee);
        Predicate finalFeePredicate = builder.lessThanOrEqualTo(root.get("deliveryFee"), finalFee);

        criteria.where(namePredicate, initialFeePredicate, finalFeePredicate);

        TypedQuery<Restaurant> query = entityManager.createQuery(criteria);

        return query.getResultList();
    }
}
