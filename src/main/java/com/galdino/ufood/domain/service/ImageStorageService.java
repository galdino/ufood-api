package com.galdino.ufood.domain.service;

import lombok.Builder;
import lombok.Getter;
import org.apache.logging.log4j.util.Strings;

import java.io.InputStream;
import java.util.UUID;

public interface ImageStorageService {

    void storage(NewImage newImage);
    void remove(String fileName);

    InputStream recover(String fileName);

    default void replace(String oldFileName, NewImage newImage) {
        storage(newImage);

        if (Strings.isNotBlank(oldFileName)) {
            remove(oldFileName);
        }
    }

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
