package com.chuckcha.tt.notificationservice.handler;

import com.chuckcha.tt.core.event.EmailSendingEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "EMAIL_SENDING_TASKS", containerFactory = "kafkaListenerContainerFactory")
@Slf4j
public class EmailSendingEventHandler {

    @KafkaHandler
    public void handle(@Payload EmailSendingEvent event) {
        //TODO: email sending logic
        log.info(event.toString());
    }
}
