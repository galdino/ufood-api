package com.galdino.ufood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    NOT_UNDERSTANDABLE_MESSAGE("Not understandable message", "/not-understandable-message"),
    RESOURCE_NOT_FOUND("Resource not found", "/resource-not-found"),
    ENTITY_IN_USE("Entity in use", "/entity-in-use"),
    BUSINESS_ERROR("Business rule violation", "/business-error"),
    INVALID_PARAMETER("Invalid Parameter", "/invalid-parameter"),
    SYSTEM_ERROR("System error", "/system-error"),
    INVALID_FIELD("Invalid field", "/invalid-field");

    private String title;
    private String uri;

    ProblemType(String title, String path) {
        this.title = title;
        this.uri = "https://ufood.com" + path;
    }
}
