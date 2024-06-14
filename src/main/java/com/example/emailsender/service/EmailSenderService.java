package com.example.emailsender.service;

import com.example.emailsender.data.Email;
import com.example.emailsender.exceptions.EmailSendingException;
import com.example.emailsender.messaging.SendEmailMessage;
import com.example.emailsender.repository.EmailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailRepository emailRepository;

    @Value("${email.host.username}")
    private String from;

    @Value("${email.recipient.username}")
    private String to;



    public void processSendEmail(SendEmailMessage sendEmailMessage) {
        Email email = Email.builder()
                .from(from)
                .to(to)
                .subject(sendEmailMessage.getSubject())
                .body(sendEmailMessage.getBody())
                .build();

        sendEmail(email);
    }

    public void sendEmail(Email email) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(email.getTo());
            message.setSubject(email.getSubject());
            message.setText(email.getBody());

            log.info("Message to send: {}", message);
            mailSender.send(message);

            email.setStatus("SENT");
            email.setError(null);

        } catch (Exception e) {
            log.info("Error sending email: {}", e.getMessage());
            email.setStatus("FAILED");
            email.setError(e.getClass().getName() + ": " + e.getMessage());
            throw new EmailSendingException(e.getMessage(), e.getCause());
        } finally {
            log.info(email.getStatus());
            emailRepository.save(email);
        }
    }
}
