package com.galdino.ufood.infrastructure.service.email;

import com.galdino.ufood.core.validation.email.EmailProperties;
import com.galdino.ufood.domain.service.EmailSenderService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import java.util.Set;

public class SmtpEmailSenderService implements EmailSenderService {

    public EmailProperties emailProperties;
    private JavaMailSender javaMailSender;
    private Configuration freemarkerConfig;

    public SmtpEmailSenderService(EmailProperties emailProperties, JavaMailSender javaMailSender, Configuration freemarkerConfig) {
        this.emailProperties = emailProperties;
        this.javaMailSender = javaMailSender;
        this.freemarkerConfig = freemarkerConfig;
    }

    @Override
    public void send(Message message) {
        callSendMessage(message);
    }

    public void callSendMessage(Message message) {
        sendMessage(message, message.getRecipients());
    }

    public void sendMessage(Message message, Set<String> recipients) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
            mimeMessageHelper.setFrom(emailProperties.getSender());
            mimeMessageHelper.setTo(recipients.toArray(new String[0]));
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
            Template template = freemarkerConfig.getTemplate(message.getBody());

            return FreeMarkerTemplateUtils.processTemplateIntoString(template, message.getVariables());
        } catch (Exception e) {
            throw new EmailException("Smtp email sender process template error.", e);
        }
    }
}
