package com.galdino.ufood.domain.service;

import com.galdino.ufood.domain.exception.EntityInUseException;
import com.galdino.ufood.domain.exception.UEntityNotFoundException;
import com.galdino.ufood.domain.model.State;
import com.galdino.ufood.domain.repository.StateRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class StateRegisterService {
    public static final String UNABLE_TO_FIND_STATE = "Unable to find state with id %d";
    private StateRepository stateRepository;

    public StateRegisterService(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    public State add(State state) {
        return stateRepository.save(state);
    }

    public void remove(Long id) {
        try {
            stateRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new UEntityNotFoundException(String.format(UNABLE_TO_FIND_STATE, id));
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format("State with id %d cannot be removed, it is in use." , id));
        }
    }

    public State findOrThrow(Long id) {
        return stateRepository.findById(id).orElseThrow(() -> new UEntityNotFoundException(String.format(UNABLE_TO_FIND_STATE, id)));
    }
}
