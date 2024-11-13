package com.galdino.ufood.domain.service;

import com.galdino.ufood.domain.model.UOrder;
import com.galdino.ufood.domain.model.UOrderStatus;
import com.galdino.ufood.domain.repository.UOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FlowUOrderService {

    private final UOrderRegisterService uOrderRegisterService;
    private final UOrderRepository uOrderRepository;

    public FlowUOrderService(UOrderRegisterService uOrderRegisterService, EmailSenderService emailSenderService, UOrderRepository uOrderRepository) {
        this.uOrderRegisterService = uOrderRegisterService;
        this.uOrderRepository = uOrderRepository;
    }

    @Transactional
    public void confirm(String code) {
        UOrder uOrder = uOrderRegisterService.findOrThrow(code);
        uOrder.getStatusSituation().confirm(uOrder, UOrderStatus.CONFIRMED);

        uOrderRepository.save(uOrder);
    }

    @Transactional
    public void cancel(String code) {
        UOrder uOrder = uOrderRegisterService.findOrThrow(code);
        uOrder.getStatusSituation().cancel(uOrder, UOrderStatus.CANCELED);

        uOrderRepository.save(uOrder);
    }

    @Transactional
    public void deliver(String code) {
        UOrder uOrder = uOrderRegisterService.findOrThrow(code);
        uOrder.getStatusSituation().deliver(uOrder, UOrderStatus.DELIVERED);
    }
}
