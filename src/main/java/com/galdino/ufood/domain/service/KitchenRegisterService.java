package com.galdino.ufood.domain.service;

import com.galdino.ufood.domain.exception.EntityInUseException;
import com.galdino.ufood.domain.exception.UEntityNotFoundException;
import com.galdino.ufood.domain.model.Kitchen;
import com.galdino.ufood.domain.repository.KitchenRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class KitchenRegisterService {
    public static final String UNABLE_TO_FIND_KITCHEN = "Unable to find kitchen with id %d";
    public static final String KITCHEN_IN_USE = "Kitchen with id %d cannot be removed, it is in use.";

    private KitchenRepository kitchenRepository;

    public KitchenRegisterService(KitchenRepository kitchenRepository) {
        this.kitchenRepository = kitchenRepository;
    }

    public Kitchen add(Kitchen kitchen) {
        return kitchenRepository.save(kitchen);
    }

    public void remove(Long id) {
        try {
            kitchenRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new UEntityNotFoundException(String.format(UNABLE_TO_FIND_KITCHEN, id));
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format(KITCHEN_IN_USE, id));
        }
    }

    public Kitchen findOrThrow(Long id) {
        return kitchenRepository.findById(id).orElseThrow(() -> new UEntityNotFoundException(String.format(UNABLE_TO_FIND_KITCHEN, id)));
    }
}
