package com.galdino.ufood.domain.service;

import com.galdino.ufood.domain.model.City;
import com.galdino.ufood.domain.model.State;
import com.galdino.ufood.domain.repository.CityRepository;
import com.galdino.ufood.domain.repository.StateRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class CityRegisterService {

    private CityRepository cityRepository;
    private StateRepository stateRepository;

    public CityRegisterService(CityRepository cityRepository, StateRepository stateRepository) {
        this.cityRepository = cityRepository;
        this.stateRepository = stateRepository;
    }

    public City add(City city) {
        Long stateId = city.getState().getId();
        State state = stateRepository.findById(stateId);

        if (state == null) {
            throw new EntityNotFoundException(String.format("Unable to find state with id %d", stateId));
        }

        city.setState(state);
        return cityRepository.add(city);
    }

    public void remove(Long id) {
        try {
            cityRepository.delete(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(String.format("Unable to find city with id %d", id));
        }
    }
}
