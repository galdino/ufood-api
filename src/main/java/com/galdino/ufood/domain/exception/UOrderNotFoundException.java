package com.galdino.ufood.domain.exception;

public class UOrderNotFoundException extends UEntityNotFoundException {
    public UOrderNotFoundException(String message) {
        super(message);
    }

    public UOrderNotFoundException(Long id) {
        this(String.format("Unable to find uorder with id %d", id));
    }
}
