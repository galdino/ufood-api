package com.galdino.ufood.infrastructure.repository;

import com.galdino.ufood.domain.model.Restaurant;
import com.galdino.ufood.domain.repository.RestaurantRepositoryQueries;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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
        criteria.from(Restaurant.class);

        TypedQuery<Restaurant> query = entityManager.createQuery(criteria);

        return query.getResultList();
    }
}
