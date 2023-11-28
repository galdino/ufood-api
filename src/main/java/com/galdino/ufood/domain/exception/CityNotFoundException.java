package com.galdino.ufood.domain.exception;

public class CityNotFoundException extends UEntityNotFoundException {
    public CityNotFoundException(String message) {
        super(message);
    }

    public CityNotFoundException(Long id) {
        this(String.format("Unable to find city with id %d", id));
    }
}
