package com.galdino.ufood.core.validation.email;

import com.galdino.ufood.domain.service.EmailSenderService;
import com.galdino.ufood.infrastructure.service.email.FakeEmailSenderService;
import com.galdino.ufood.infrastructure.service.email.SmtpEmailSenderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.galdino.ufood.core.validation.email.EmailProperties.EmailSenderType;

@Configuration
public class EmailSenderConfig {

    private final EmailProperties emailProperties;

    public EmailSenderConfig(EmailProperties emailProperties) {
        this.emailProperties = emailProperties;
    }

    @Bean
    public EmailSenderService emailSenderService() {
        if (EmailSenderType.SMTP.equals(emailProperties.getType())) {
            return new SmtpEmailSenderService();
        } else {
            return new FakeEmailSenderService();
        }
    }
}
