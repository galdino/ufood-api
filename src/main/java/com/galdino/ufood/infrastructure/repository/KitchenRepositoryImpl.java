package com.galdino.ufood.infrastructure.repository;

import com.galdino.ufood.domain.model.Kitchen;
import com.galdino.ufood.domain.repository.KitchenRepository;
import org.springframework.dao.EmptyResultDataAccessException;
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
    public List<Kitchen> findByName(String name) {
        return manager.createQuery("from Kitchen where name like :name", Kitchen.class)
                      .setParameter("name", "%" + name + "%")
                      .getResultList();
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
    public void delete(Long id) {
        Kitchen kitchen = findById(id);

        if (kitchen == null) {
            throw new EmptyResultDataAccessException(1);
        }

        manager.remove(kitchen);
    }
}
