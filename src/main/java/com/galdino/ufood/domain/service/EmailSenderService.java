package com.galdino.ufood.domain.service;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

import java.util.Set;

public interface EmailSenderService {

    void send(Message message);

    @Builder
    @Getter
    class Message {
        @Singular
        private Set<String> recipients;

        @NonNull
        private String subject;

        @NonNull
        private String body;
    }
}
