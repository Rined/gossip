package com.rined.gossip.services;

import com.rined.gossip.properties.MailProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class MailSenderImpl implements MailSender {
    private final JavaMailSender mailSender;
    private final MailProperties mailProperties;

    @Override
    public void sendHtml(String mailTo, String subject, String message) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mailMessage = new MimeMessageHelper(mimeMessage);
            mailMessage.setTo(mailTo);
            mailMessage.setFrom(mailProperties.getUsername());
            mailMessage.setSubject(subject);
            mailMessage.setText(message, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            send(mailTo, subject, message);
        }
    }

    @Override
    public void send(String mailTo, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(mailProperties.getUsername());
        mailMessage.setTo(mailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }
}
