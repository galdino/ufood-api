package com.galdino.ufood.domain.service;

import com.galdino.ufood.domain.exception.CityNotFoundException;
import com.galdino.ufood.domain.exception.EntityInUseException;
import com.galdino.ufood.domain.model.City;
import com.galdino.ufood.domain.model.State;
import com.galdino.ufood.domain.repository.CityRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CityRegisterService {

    private CityRepository cityRepository;
    private StateRegisterService stateRegisterService;

    public CityRegisterService(CityRepository cityRepository, StateRegisterService stateRegisterService) {
        this.cityRepository = cityRepository;
        this.stateRegisterService = stateRegisterService;
    }

    @Transactional
    public City add(City city) {
        Long stateId = city.getState().getId();
        State state = stateRegisterService.findOrThrow(stateId);

        city.setState(state);
        return cityRepository.save(city);
    }

    @Transactional
    public void remove(Long id) {
        try {
            cityRepository.deleteById(id);
            cityRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new CityNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format("City with id %d cannot be removed, it is in use.", id));
        }
    }

    public City findOrThrow(Long id) {
        return cityRepository.findById(id).orElseThrow(() -> new CityNotFoundException(id));
    }
}
