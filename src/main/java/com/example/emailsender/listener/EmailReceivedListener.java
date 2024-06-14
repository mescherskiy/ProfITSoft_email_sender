package com.example.emailsender.listener;

import com.example.emailsender.messaging.SendEmailMessage;
import com.example.emailsender.service.EmailSenderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailReceivedListener {
    private final EmailSenderService emailSenderService;
    private final ObjectMapper jacksonObjectMapper;

    @KafkaListener(topics = "${kafka.topic.sendEmail}")
    public void listen(ConsumerRecord<String, String> record) {

        try {
            SendEmailMessage message = jacksonObjectMapper.readValue(record.value(), SendEmailMessage.class);
            emailSenderService.processSendEmail(message);

        } catch (Exception e) {
            //TODO: IllegalArgumentException
            e.printStackTrace();
        }
    }

}
