package com.galdino.ufood.domain.exception;

public class RestaurantNotFoundException extends UEntityNotFoundException {
    public RestaurantNotFoundException(String message) {
        super(message);
    }

    public RestaurantNotFoundException(Long id) {
        this(String.format("Unable to find restaurant with id %d", id));
    }
}
