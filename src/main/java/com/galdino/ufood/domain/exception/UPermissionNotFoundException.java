package com.galdino.ufood.domain.exception;

public class UPermissionNotFoundException extends UEntityNotFoundException {

    public UPermissionNotFoundException(String message) {
        super(message);
    }

    public UPermissionNotFoundException(Long id) {
        this(String.format("Unable to find upermission with id %d", id));
    }
}
