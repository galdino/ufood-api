package com.galdino.ufood.domain.service;

import com.galdino.ufood.domain.exception.EntityInUseException;
import com.galdino.ufood.domain.model.State;
import com.galdino.ufood.domain.repository.StateRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class StateRegisterService {
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
            throw new EntityNotFoundException(String.format("Unable to find state with id %d", id));
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format("State with id %d cannot be removed, it is in use." , id));
        }
    }
}
