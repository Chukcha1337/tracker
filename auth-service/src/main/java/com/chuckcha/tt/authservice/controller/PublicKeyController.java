package com.chuckcha.tt.authservice.controller;

import com.chuckcha.tt.authservice.config.JwtProperties;
import com.chuckcha.tt.core.auth.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.interfaces.RSAPublicKey;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class PublicKeyController {

    private final JwtProperties props;

    @GetMapping("/public-key")
    public ResponseEntity<String> getPublicKey() throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream(props.getPublicKeyPath());
        if (is == null) {
            return ResponseEntity.notFound().build();
        }
        String key = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        return ResponseEntity.ok(key);
    }
}
