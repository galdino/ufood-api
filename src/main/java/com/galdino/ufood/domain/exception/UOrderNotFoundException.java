package com.galdino.ufood.domain.exception;

public class UOrderNotFoundException extends UEntityNotFoundException {
    public UOrderNotFoundException(String code) {
        super(String.format("Unable to find uorder with code %s", code));
    }
}
