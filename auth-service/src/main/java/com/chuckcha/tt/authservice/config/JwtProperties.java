package com.chuckcha.tt.authservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "security.jwt")
public class JwtProperties {

    private String privateKeyPath;
    private String publicKeyPath;
    private long accessTokenExpiration;
    private long refreshTokenExpiration;

}
