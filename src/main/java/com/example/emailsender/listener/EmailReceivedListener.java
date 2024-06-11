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

        String key = record.key();
        String value = record.value();

        System.out.println("Received message: Key = " + key + ", Value = " + value);
        try {
            SendEmailMessage message = jacksonObjectMapper.readValue(value, SendEmailMessage.class);
            System.out.println("Subject: " + message.getSubject());
            System.out.println("Body: " + message.getBody());
            log.info("Subject: {}", message.getSubject());
            log.info("Body: {}", message.getBody());

            // Обработка полученного сообщения
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
