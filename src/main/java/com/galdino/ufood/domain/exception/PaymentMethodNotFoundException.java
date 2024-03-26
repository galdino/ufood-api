package com.galdino.ufood.domain.exception;

public class PaymentMethodNotFoundException extends UEntityNotFoundException {
    public PaymentMethodNotFoundException(String message) {
        super(message);
    }

    public PaymentMethodNotFoundException(Long id) {
        this(String.format("Unable to find payment method with id %d", id));
    }
}
