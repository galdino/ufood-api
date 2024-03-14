package com.galdino.ufood.domain.service;

import com.galdino.ufood.domain.exception.EntityInUseException;
import com.galdino.ufood.domain.exception.KitchenNotFoundException;
import com.galdino.ufood.domain.model.Kitchen;
import com.galdino.ufood.domain.repository.KitchenRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KitchenRegisterService {
    public static final String KITCHEN_IN_USE = "Kitchen with id %d cannot be removed, it is in use.";

    private KitchenRepository kitchenRepository;

    public KitchenRegisterService(KitchenRepository kitchenRepository) {
        this.kitchenRepository = kitchenRepository;
    }

    @Transactional
    public Kitchen add(Kitchen kitchen) {
        return kitchenRepository.save(kitchen);
    }

    @Transactional
    public void remove(Long id) {
        try {
            kitchenRepository.deleteById(id);
            kitchenRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new KitchenNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format(KITCHEN_IN_USE, id));
        }
    }

    public Kitchen findOrThrow(Long id) {
        return kitchenRepository.findById(id).orElseThrow(() -> new KitchenNotFoundException(id));
    }
}
