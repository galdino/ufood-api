package com.galdino.ufood.domain.service;

import com.galdino.ufood.domain.exception.EntityInUseException;
import com.galdino.ufood.domain.exception.UEntityNotFoundException;
import com.galdino.ufood.domain.model.City;
import com.galdino.ufood.domain.model.State;
import com.galdino.ufood.domain.repository.CityRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CityRegisterService {

    public static final String UNABLE_TO_FIND_CITY = "Unable to find city with id %d";
    private CityRepository cityRepository;
    private StateRegisterService stateRegisterService;

    public CityRegisterService(CityRepository cityRepository, StateRegisterService stateRegisterService) {
        this.cityRepository = cityRepository;
        this.stateRegisterService = stateRegisterService;
    }

    public City add(City city) {
        Long stateId = city.getState().getId();
        State state = stateRegisterService.findOrThrow(stateId);

        city.setState(state);
        return cityRepository.save(city);
    }

    public void remove(Long id) {
        try {
            cityRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new UEntityNotFoundException(String.format(UNABLE_TO_FIND_CITY, id));
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format("City with id %d cannot be removed, it is in use.", id));
        }
    }

    public City findOrThrow(Long id) {
        return cityRepository.findById(id).orElseThrow(() -> new UEntityNotFoundException(String.format(UNABLE_TO_FIND_CITY, id)));
    }
}
