package com.chuckcha.tt.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "app.auth")
public class AuthProperties {

    private List<String> whitelist;
}
