package com.chuckcha.tt.notificationservice.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spring.kafka.consumer")
public class KafkaProperties {

    private String bootstrapServers;
    private String groupId;
    private String autoOffsetReset;
    private String isolationLevel;
    private String trustedPackages;
    private String topicName;
}
