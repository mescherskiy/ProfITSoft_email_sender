package com.example.emailsender.scheduler;

import com.example.emailsender.service.RetryEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailRetryScheduler {

    @Autowired
    private RetryEmailService retryEmailService;

    @Scheduled(fixedRate = 300000)
    public void scheduleEmailRetry() {
        retryEmailService.retryFailedEmails();
    }
}
