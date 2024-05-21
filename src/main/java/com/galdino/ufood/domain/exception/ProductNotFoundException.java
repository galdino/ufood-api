package com.galdino.ufood.domain.exception;

public class ProductNotFoundException extends UEntityNotFoundException {

    public ProductNotFoundException(String message) {
        super(message);
    }
    public ProductNotFoundException(Long id) {
        this(String.format("Unable to find product with id %d", id));
    }
}
