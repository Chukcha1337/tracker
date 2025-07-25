package com.chuckcha.tt.schedulerservice.publisher;

import com.chuckcha.tt.core.event.EmailSendingEvent;
import com.chuckcha.tt.schedulerservice.config.KafkaProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final KafkaProperties props;

    public void publish(EmailSendingEvent event) {
        kafkaTemplate.executeInTransaction(kt -> {
            kt.send(props.getTopicName(), event);
            return true;
        });
    }

}
