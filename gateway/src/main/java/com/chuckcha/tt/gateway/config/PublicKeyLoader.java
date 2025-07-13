package com.chuckcha.tt.gateway.config;

import com.chuckcha.tt.core.auth.JwtUtils;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPublicKey;

@Component
@Getter
@Lazy
public class PublicKeyLoader {

    @Value("${security.jwt.public-key-path}")
    private String keyPath;

    private RSAPublicKey publicKey;

    @PostConstruct
    public void init() {
        this.publicKey = JwtUtils.loadPublicKey(keyPath);
    }
}
