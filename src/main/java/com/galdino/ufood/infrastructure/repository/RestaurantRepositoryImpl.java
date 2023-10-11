package com.galdino.ufood.infrastructure.repository;

import com.galdino.ufood.domain.model.Restaurant;
import com.galdino.ufood.domain.repository.RestaurantRepositoryQueries;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepositoryQueries {

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<Restaurant> find(String name, BigDecimal initialFee, BigDecimal finalFee) {

        var jpql = new StringBuilder();
        jpql.append("from Restaurant where 1 = 1 ");

        var parameters = new HashMap<String, Object>();

        if (name != null && !"".equals(name.trim())) {
            jpql.append("and name like :name ");
            parameters.put("name", "%" + name + "%");
        }

        if (initialFee != null) {
            jpql.append(" and deliveryFee >= :initialFee ");
            parameters.put("initialFee", initialFee);
        }

        if (finalFee != null) {
            jpql.append(" and deliveryFee <= :finalFee ");
            parameters.put("finalFee", finalFee);
        }

        TypedQuery<Restaurant> query = entityManager.createQuery(jpql.toString(), Restaurant.class);

        parameters.forEach((key, value) -> query.setParameter(key, value));

        return query.getResultList();
    }
}
