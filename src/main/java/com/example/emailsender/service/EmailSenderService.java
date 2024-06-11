package com.example.emailsender.service;

import com.example.emailsender.data.Email;
import com.example.emailsender.messaging.SendEmailMessage;
import com.example.emailsender.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

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
            message.setTo(email.getTo());
            message.setSubject(email.getSubject());
            message.setText(email.getBody());

            mailSender.send(message);

            email.setStatus("SENT");
            email.setError(null);
        } catch (Exception e) {
            email.setStatus("FAILED");
            email.setError(e.getClass().getName() + ": " + e.getMessage());
        }

        emailRepository.save(email);
    }
}
