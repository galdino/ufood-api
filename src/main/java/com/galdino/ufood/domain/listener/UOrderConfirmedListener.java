package com.galdino.ufood.domain.listener;

import com.galdino.ufood.domain.event.UOrderConfirmedEvent;
import com.galdino.ufood.domain.model.UOrder;
import com.galdino.ufood.domain.service.EmailSenderService;
import com.galdino.ufood.domain.service.EmailSenderService.Message;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class UOrderConfirmedListener {

    private final EmailSenderService emailSenderService;

    public UOrderConfirmedListener(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void whenConfirmingUOrder(UOrderConfirmedEvent event) {

//        if(true) throw new RuntimeException();

        UOrder uOrder = event.getUOrder();

        String subject = String.format("%s - Order confirmation", uOrder.getRestaurant().getName());

        Message message = Message.builder()
                                 .subject(subject)
                                 .body("order-confirmed.html")
                                 .variable("uorder", uOrder)
                                 .recipient(uOrder.getUser().getEmail())
                                 .build();

        emailSenderService.send(message);

    }
}
