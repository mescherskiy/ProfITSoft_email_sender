package com.example.emailsender.service;

import com.example.emailsender.data.Email;
import com.example.emailsender.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RetryEmailService {

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private EmailRepository emailRepository;

    public void retryFailedEmails() {
        List<Email> failedEmails = emailRepository.findByStatus("FAILED");
        for (Email email : failedEmails) {
            email.setAttemptCount(email.getAttemptCount() + 1);
            email.setLastAttemptTime(System.currentTimeMillis());
            emailSenderService.sendEmail(email);
        }
    }
}
