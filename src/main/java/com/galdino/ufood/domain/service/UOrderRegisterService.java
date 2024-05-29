package com.galdino.ufood.domain.service;

import com.galdino.ufood.domain.exception.UOrderNotFoundException;
import com.galdino.ufood.domain.model.UOrder;
import com.galdino.ufood.domain.repository.UOrderRepository;
import org.springframework.stereotype.Service;

@Service
public class UOrderRegisterService {

    private final UOrderRepository uOrderRepository;

    public UOrderRegisterService(UOrderRepository uOrderRepository) {
        this.uOrderRepository = uOrderRepository;
    }

    public UOrder findOrThrow(Long id) {
        return uOrderRepository.findById(id)
                               .orElseThrow(() -> new UOrderNotFoundException(id));
    }

}
