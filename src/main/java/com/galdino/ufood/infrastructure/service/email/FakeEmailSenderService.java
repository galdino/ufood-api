package com.galdino.ufood.infrastructure.service.email;

import com.galdino.ufood.core.validation.email.EmailProperties;
import com.galdino.ufood.domain.service.EmailSenderService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

@Service
@Slf4j
public class FakeEmailSenderService implements EmailSenderService {

    @Autowired
    private EmailProperties emailProperties;
    @Autowired
    private Configuration freemarkerConfig;

    @Override
    public void send(Message message) {

        try {

            log.info("Sending email...");
            log.info(String.format("Sender: %s", emailProperties.getSender()));
            log.info(String.format("Recipients: %s", message.getRecipients().toArray(new String[0])));
            log.info(String.format("Subject: %s", message.getSubject()));

            String body = processTemplate(message);

            log.info(String.format("Body: %s", body));
        } catch (Exception e) {
            throw new EmailException("Fake email sender error.", e);
        }

    }

    private String processTemplate(Message message) {
        try {
            Template template = freemarkerConfig.getTemplate(message.getBody());

            return FreeMarkerTemplateUtils.processTemplateIntoString(template, message.getVariables());
        } catch (Exception e) {
            throw new EmailException("Fake email sender process template error.", e);
        }
    }
}
