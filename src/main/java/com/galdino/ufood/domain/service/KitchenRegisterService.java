package com.galdino.ufood.domain.service;

import com.galdino.ufood.domain.model.Kitchen;
import com.galdino.ufood.domain.repository.KitchenRepository;
import org.springframework.stereotype.Service;

@Service
public class KitchenRegisterService {
    private KitchenRepository kitchenRepository;

    public KitchenRegisterService(KitchenRepository kitchenRepository) {
        this.kitchenRepository = kitchenRepository;
    }

    public Kitchen add(Kitchen kitchen) {
        return kitchenRepository.add(kitchen);
    }
}
