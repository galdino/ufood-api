package com.galdino.ufood.infrastructure.repository;

import com.galdino.ufood.domain.model.City;
import com.galdino.ufood.domain.repository.CityRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CityRepositoryImpl implements CityRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<City> list() {
        return manager.createQuery("from City", City.class).getResultList();
    }

    @Override
    public City findById(Long id) {
        return manager.find(City.class, id);
    }

    @Transactional
    @Override
    public City add(City city) {
        return manager.merge(city);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        City city = findById(id);

        if (city == null) {
            throw new EmptyResultDataAccessException(1);
        }

        manager.remove(city);
    }
}
