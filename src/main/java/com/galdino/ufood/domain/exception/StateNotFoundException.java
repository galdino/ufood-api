package com.galdino.ufood.domain.exception;

public class StateNotFoundException extends UEntityNotFoundException {
    public StateNotFoundException(String message) {
        super(message);
    }

    public StateNotFoundException(Long id) {
        this(String.format("Unable to find state with id %d", id));
    }
}
