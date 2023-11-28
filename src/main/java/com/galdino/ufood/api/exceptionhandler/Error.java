package com.galdino.ufood.api.exceptionhandler;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Error {
    private LocalDateTime dateTime;
    private String message;
}
