package com.galdino.ufood.core.validation.email;

import com.galdino.ufood.domain.service.EmailSenderService;
import com.galdino.ufood.infrastructure.service.email.FakeEmailSenderService;
import com.galdino.ufood.infrastructure.service.email.SandboxEmailSenderService;
import com.galdino.ufood.infrastructure.service.email.SmtpEmailSenderService;
import freemarker.template.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;

@org.springframework.context.annotation.Configuration
public class EmailSenderConfig {

    private final EmailProperties emailProperties;
    private final JavaMailSender javaMailSender;
    private final Configuration freemarkerConfig;

    public EmailSenderConfig(EmailProperties emailProperties, JavaMailSender javaMailSender, Configuration freemarkerConfig) {
        this.emailProperties = emailProperties;
        this.javaMailSender = javaMailSender;
        this.freemarkerConfig = freemarkerConfig;
    }

    @Bean
    public EmailSenderService emailSenderService() {

        switch (emailProperties.getType()) {
            case SMTP:
                return new SmtpEmailSenderService(emailProperties, javaMailSender, freemarkerConfig);
            case FAKE:
                return new FakeEmailSenderService(emailProperties, freemarkerConfig);
            case SANDBOX:
                return new SandboxEmailSenderService(emailProperties, javaMailSender, freemarkerConfig);
            default:
                throw new RuntimeException();
        }

    }
}
