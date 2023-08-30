package com.galdino.ufood.infrastructure.repository;

import com.galdino.ufood.domain.model.Kitchen;
import com.galdino.ufood.domain.repository.KitchenRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class KitchenRepositoryImpl implements KitchenRepository {
    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Kitchen> list() {
        return manager.createQuery("from Kitchen", Kitchen.class).getResultList();
    }

    @Override
    public Kitchen findById(Long id) {
        return manager.find(Kitchen.class, id);
    }

    @Transactional
    @Override
    public Kitchen add(Kitchen kitchen) {
        return manager.merge(kitchen);
    }

    @Transactional
    @Override
    public void delete(Kitchen kitchen) {
        kitchen = findById(kitchen.getId());
        manager.remove(kitchen);
    }
}
