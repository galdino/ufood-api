package com.galdino.ufood.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.util.UUID;

public interface ImageStorageService {

    void storage(NewImage newImage);

    default String createFileName(String originalName) {
        return UUID.randomUUID().toString() + "_" + originalName;
    }

    @Builder
    @Getter
    class NewImage {
        private String fileName;
        private InputStream inputStream;
    }

}
