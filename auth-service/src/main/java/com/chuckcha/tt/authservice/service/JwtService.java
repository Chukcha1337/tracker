package com.chuckcha.tt.authservice.service;

import com.chuckcha.tt.authservice.config.JwtProperties;
import com.chuckcha.tt.core.user.UserResponse;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Key;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties props;

    public String generateToken(UserResponse user) {
        return "access-token";
    }

    public String generateRefreshToken(UserResponse user) {
        // TODO: генерировать refresh-token
        return "refresh-token";
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(props.getSecretKey().getBytes());
    }
}
