package com.galdino.ufood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

//@ResponseStatus(HttpStatus.NOT_FOUND)
public class UEntityNotFoundException extends ResponseStatusException {
    public UEntityNotFoundException(String message) {
        this(HttpStatus.NOT_FOUND, message);
    }

    public UEntityNotFoundException(HttpStatus status, String reason) {
        super(status, reason);
    }
}
