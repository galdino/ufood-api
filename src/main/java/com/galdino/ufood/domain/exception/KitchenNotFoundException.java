package com.galdino.ufood.domain.exception;

public class KitchenNotFoundException extends UEntityNotFoundException {
    public KitchenNotFoundException(String message) {
        super(message);
    }

    public KitchenNotFoundException(Long id) {
        this(String.format("Unable to find kitchen with id %d", id));
    }
}
