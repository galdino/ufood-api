package com.galdino.ufood.core.validation.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
@Getter
@Setter
@Component
@ConfigurationProperties("ufood.email")
public class EmailProperties {

    @NotNull
    private String sender;
    private EmailSenderType type = EmailSenderType.FAKE;
    private String recipients;

    public enum EmailSenderType {
        FAKE, SMTP, SANDBOX
    }

}
