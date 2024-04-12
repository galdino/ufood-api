package com.galdino.ufood.domain.exception;

public class UserNotFoundException extends UEntityNotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(Long id) {
        this(String.format("Unable to find user with id %d", id));
    }
}
