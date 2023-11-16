package com.galdino.ufood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

//@ResponseStatus(HttpStatus.CONFLICT)
public class EntityInUseException extends ResponseStatusException {
    public EntityInUseException(String message) {
        this(HttpStatus.CONFLICT, message);
    }

    public EntityInUseException(HttpStatus status, String reason) {
        super(status, reason);
    }
}
