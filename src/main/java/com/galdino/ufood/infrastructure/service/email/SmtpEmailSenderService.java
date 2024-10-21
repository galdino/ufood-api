package com.galdino.ufood.infrastructure.service.email;

import com.galdino.ufood.core.validation.email.EmailProperties;
import com.galdino.ufood.domain.service.EmailSenderService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;

@Service
public class SmtpEmailSenderService implements EmailSenderService {

    private final JavaMailSender javaMailSender;
    private final EmailProperties emailProperties;
    private final Configuration freemmarkerConfig;

    public SmtpEmailSenderService(JavaMailSender javaMailSender, EmailProperties emailProperties, Configuration freemmarkerConfig) {
        this.javaMailSender = javaMailSender;
        this.emailProperties = emailProperties;
        this.freemmarkerConfig = freemmarkerConfig;
    }

    @Override
    public void send(Message message) {

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
            mimeMessageHelper.setFrom(emailProperties.getSender());
            mimeMessageHelper.setTo(message.getRecipients().toArray(new String[0]));
            mimeMessageHelper.setSubject(message.getSubject());

            String body = processTemplate(message);

            mimeMessageHelper.setText(body, true);

            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new EmailException("Smtp email sender error.", e);
        }

    }

    private String processTemplate(Message message) {
        try {
            Template template = freemmarkerConfig.getTemplate(message.getBody());

            return FreeMarkerTemplateUtils.processTemplateIntoString(template, message.getVariables());
        } catch (Exception e) {
            throw new EmailException("Smtp email sender process template error.", e);
        }
    }
}
