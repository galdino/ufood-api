package com.galdino.ufood.domain.listener;

import com.galdino.ufood.domain.event.UOrderCanceledEvent;
import com.galdino.ufood.domain.model.UOrder;
import com.galdino.ufood.domain.service.EmailSenderService;
import com.galdino.ufood.domain.service.EmailSenderService.Message;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class UOrderCanceledListener {

    private final EmailSenderService emailSenderService;

    public UOrderCanceledListener(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void whenCancelingUOrder(UOrderCanceledEvent event) {

//        if(true) throw new RuntimeException();

        UOrder uOrder = event.getUOrder();

        String subject = String.format("%s - Order cancellation", uOrder.getRestaurant().getName());

        Message message = Message.builder()
                                 .subject(subject)
                                 .body("order-email.html")
                                 .variable("uorder", uOrder)
                                 .recipient(uOrder.getUser().getEmail())
                                 .build();

        emailSenderService.send(message);

    }
}
