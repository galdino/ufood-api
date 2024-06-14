package com.galdino.ufood.domain.model;

public enum UOrderStatus {
    CREATED("Created"),
    CONFIRMED("Confirmed"),
    DELIVERED("Delivered"),
    CANCELED("Canceled");

    private final String description;

    UOrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
