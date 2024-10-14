package com.galdino.ufood.infrastructure.service.email;

import com.galdino.ufood.core.validation.email.EmailProperties;
import com.galdino.ufood.domain.service.EmailSenderService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class SmtpEmailSenderService implements EmailSenderService {

    private final JavaMailSender javaMailSender;
    private final EmailProperties emailProperties;

    public SmtpEmailSenderService(JavaMailSender javaMailSender, EmailProperties emailProperties) {
        this.javaMailSender = javaMailSender;
        this.emailProperties = emailProperties;
    }

    @Override
    public void send(Message message) {

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
            mimeMessageHelper.setFrom(emailProperties.getSender());
            mimeMessageHelper.setTo(message.getRecipients().toArray(new String[0]));
            mimeMessageHelper.setSubject(message.getSubject());
            mimeMessageHelper.setText(message.getBody(), true);

            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new EmailException("Smtp email sender error.", e);
        }

    }
}
