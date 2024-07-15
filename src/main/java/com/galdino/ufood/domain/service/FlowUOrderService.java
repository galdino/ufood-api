package com.galdino.ufood.domain.service;

import com.galdino.ufood.domain.model.UOrder;
import com.galdino.ufood.domain.model.UOrderStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FlowUOrderService {

    private final UOrderRegisterService uOrderRegisterService;

    public FlowUOrderService(UOrderRegisterService uOrderRegisterService) {
        this.uOrderRegisterService = uOrderRegisterService;
    }

    @Transactional
    public void confirm(String code) {
        UOrder uOrder = uOrderRegisterService.findOrThrow(code);
        uOrder.getStatusSituation().confirm(uOrder, UOrderStatus.CONFIRMED);
    }

    @Transactional
    public void cancel(String code) {
        UOrder uOrder = uOrderRegisterService.findOrThrow(code);
        uOrder.getStatusSituation().cancel(uOrder, UOrderStatus.CANCELED);
    }

    @Transactional
    public void deliver(String code) {
        UOrder uOrder = uOrderRegisterService.findOrThrow(code);
        uOrder.getStatusSituation().deliver(uOrder, UOrderStatus.DELIVERED);
    }
}
