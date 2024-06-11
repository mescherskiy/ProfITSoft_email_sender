package com.example.emailsender.messaging;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class SendEmailMessage {
    private String subject;
    private String body;
}
