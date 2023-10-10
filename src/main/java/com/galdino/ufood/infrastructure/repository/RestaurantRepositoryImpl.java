package com.galdino.ufood.infrastructure.repository;

import com.galdino.ufood.domain.model.Restaurant;
import com.galdino.ufood.domain.repository.RestaurantRepositoryQueries;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.List;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepositoryQueries {

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<Restaurant> find(String name, BigDecimal initialFee, BigDecimal finalFee) {

        var jpql = "from Restaurant where name like :name "
                +  "and deliveryFee between :initialFee and :finalFee";

        return entityManager.createQuery(jpql, Restaurant.class)
                .setParameter("name", "%" + name + "%")
                .setParameter("initialFee", initialFee)
                .setParameter("finalFee", finalFee)
                .getResultList();
    }
}
