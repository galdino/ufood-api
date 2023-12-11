package com.galdino.ufood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    NOT_UNDERSTANDABLE_MESSAGE("Not understandable message", "/not-understandable-message"),
    ENTITY_NOT_FOUND("Entity not found", "/entity-not-found"),
    ENTITY_IN_USE("Entity in use", "/entity-in-use"),
    BUSINESS_ERROR("Business rule violation", "/business-error"),
    INVALID_PARAMETER("Invalid Parameter", "/invalid-parameter");

    private String title;
    private String uri;

    ProblemType(String title, String path) {
        this.title = title;
        this.uri = "https://ufood.com" + path;
    }
}
