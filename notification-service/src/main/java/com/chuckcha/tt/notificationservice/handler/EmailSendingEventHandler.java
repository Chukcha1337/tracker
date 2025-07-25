package com.chuckcha.tt.notificationservice.handler;

import com.chuckcha.tt.core.event.EmailSendingEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "EMAIL_SENDING_TASKS", containerFactory = "kafkaListenerContainerFactory")
@Slf4j
@RequiredArgsConstructor
public class EmailSendingEventHandler {

    private final JavaMailSender mailSender;

    @KafkaHandler
    public void handle(@Payload EmailSendingEvent event) {
        log.info(event.toString());
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@sasha.com");
        message.setTo(event.email());
        message.setSubject(event.title());
        message.setText(event.description());
        mailSender.send(message);
    }
}
