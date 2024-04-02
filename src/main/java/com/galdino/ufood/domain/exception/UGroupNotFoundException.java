package com.galdino.ufood.domain.exception;

public class UGroupNotFoundException extends UEntityNotFoundException {

    public UGroupNotFoundException(String message) {
        super(message);
    }
    public UGroupNotFoundException(Long id) {
        this(String.format("Unable to find ugroup with id %d", id));
    }
}
