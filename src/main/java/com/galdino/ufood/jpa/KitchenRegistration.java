package com.galdino.ufood.jpa;

import com.galdino.ufood.domain.model.Kitchen;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class KitchenRegistration {
    @PersistenceContext
    private EntityManager manager;

    public List<Kitchen> list() {
        return manager.createQuery("from Kitchen", Kitchen.class).getResultList();
    }
}
