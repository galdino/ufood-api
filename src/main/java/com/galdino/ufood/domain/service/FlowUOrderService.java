package com.galdino.ufood.domain.service;

import com.galdino.ufood.domain.model.UOrder;
import com.galdino.ufood.domain.model.UOrderStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.galdino.ufood.domain.service.EmailSenderService.Message;

@Service
public class FlowUOrderService {

    private final UOrderRegisterService uOrderRegisterService;
    private final EmailSenderService emailSenderService;

    public FlowUOrderService(UOrderRegisterService uOrderRegisterService, EmailSenderService emailSenderService) {
        this.uOrderRegisterService = uOrderRegisterService;
        this.emailSenderService = emailSenderService;
    }

    @Transactional
    public void confirm(String code) {
        UOrder uOrder = uOrderRegisterService.findOrThrow(code);
        uOrder.getStatusSituation().confirm(uOrder, UOrderStatus.CONFIRMED);

        String subject = String.format("%s - Order confirmation", uOrder.getRestaurant().getName());
        String body = String.format("The order <strong>%s</strong> was confirmed!", uOrder.getCode());

        Message message = Message.builder()
                                 .subject(subject)
                                 .body(body)
                                 .recipient(uOrder.getUser().getEmail())
                                 .build();

        emailSenderService.send(message);
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
