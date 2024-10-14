package com.galdino.ufood.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

public interface EmailSenderService {

    void send(Message message);

    @Builder
    @Getter
    class Message {
        private Set<String> recipients;
        private String subject;
        private String body;
    }
}
