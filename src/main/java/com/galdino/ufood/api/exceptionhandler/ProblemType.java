package com.galdino.ufood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    ENTITY_NOT_FOUND("Entity not found", "/entity-not-found");

    private String title;
    private String uri;

    ProblemType(String title, String path) {
        this.title = title;
        this.uri = "https://ufood.com" + path;
    }
}
