package com.galdino.ufood.domain.service;

import com.galdino.ufood.domain.exception.EntityInUseException;
import com.galdino.ufood.domain.exception.UEntityNotFoundException;
import com.galdino.ufood.domain.model.City;
import com.galdino.ufood.domain.model.State;
import com.galdino.ufood.domain.repository.CityRepository;
import com.galdino.ufood.domain.repository.StateRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CityRegisterService {

    public static final String UNABLE_TO_FIND_CITY = "Unable to find city with id %d";
    private CityRepository cityRepository;
    private StateRepository stateRepository;

    public CityRegisterService(CityRepository cityRepository, StateRepository stateRepository) {
        this.cityRepository = cityRepository;
        this.stateRepository = stateRepository;
    }

    public City add(City city) {
        Long stateId = city.getState().getId();
        State state = stateRepository.findById(stateId)
                                     .orElseThrow(() -> new UEntityNotFoundException(String.format(StateRegisterService.UNABLE_TO_FIND_STATE, stateId)));

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
