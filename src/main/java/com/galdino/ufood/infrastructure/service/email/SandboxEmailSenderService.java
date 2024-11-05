package com.galdino.ufood.infrastructure.service.email;

import com.galdino.ufood.core.validation.email.EmailProperties;
import freemarker.template.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Collections;

public class SandboxEmailSenderService extends SmtpEmailSenderService {

    public SandboxEmailSenderService(EmailProperties emailProperties, JavaMailSender javaMailSender, Configuration freemarkerConfig) {
        super(emailProperties, javaMailSender, freemarkerConfig);
    }

    @Override
    public void callSendMessage(Message message) {
        super.sendMessage(message, Collections.singleton(emailProperties.getRecipients()));
    }

}
