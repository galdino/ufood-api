package com.galdino.ufood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public abstract class UEntityNotFoundException extends BusinessException {
    public UEntityNotFoundException(String message) {
        super(message);
    }
}
