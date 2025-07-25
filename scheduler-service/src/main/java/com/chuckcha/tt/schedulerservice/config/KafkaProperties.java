package com.chuckcha.tt.schedulerservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spring.kafka.producer")
public class KafkaProperties {

    private String bootstrapServers;
    private String acks;
    private String deliveryTimeout;
    private String requestTimeout;
    private String linger;
    private boolean enableIdempotence;
    private int maxInFlightRequests;
    private String transactionIdPrefix;
    private String topicName;



}
