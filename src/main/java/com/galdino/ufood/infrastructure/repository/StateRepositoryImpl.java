package com.galdino.ufood.infrastructure.repository;

import com.galdino.ufood.domain.model.State;
import com.galdino.ufood.domain.repository.StateRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class StateRepositoryImpl implements StateRepository {
    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<State> list() {
        return manager.createQuery("from State", State.class).getResultList();
    }

    @Override
    public State findById(Long id) {
        return manager.find(State.class, id);
    }

    @Transactional
    @Override
    public State add(State state) {
        return manager.merge(state);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        State state = findById(id);

        if (state == null) {
            throw new EmptyResultDataAccessException(1);
        }

        manager.remove(state);
    }
}
