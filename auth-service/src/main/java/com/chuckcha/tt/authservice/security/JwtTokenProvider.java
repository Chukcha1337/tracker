package com.chuckcha.tt.authservice.security;

import com.chuckcha.tt.authservice.config.JwtProperties;
import com.chuckcha.tt.core.auth.JwtUtils;
import com.chuckcha.tt.authservice.feign.UserClient;
import com.chuckcha.tt.authservice.service.AuthService;
import com.chuckcha.tt.core.auth.JwtResponse;
import com.chuckcha.tt.core.user.Role;
import com.chuckcha.tt.core.user.UserResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties props;
    private final UserClient userClient;
    private final RedisTemplate<String, String> redisTemplate;
    private RSAPrivateKey privateKey;
    private RSAPublicKey publicKey;

    private void initKeysIfNecessary() {
        if (privateKey == null || publicKey == null) {
            System.out.println("Loading keys from config...");
            this.privateKey = JwtUtils.loadPrivateKey(props.getPrivateKeyPath());
            this.publicKey = JwtUtils.loadPublicKey(props.getPublicKeyPath());
        }
    }

    public String createAccessToken(Long userId, String username, Role role) {
        initKeysIfNecessary();
        Claims claims = Jwts.claims()
                .subject(username)
                .add("id", String.valueOf(userId))
                .add("role", role)
                .build();
        Instant validity = Instant.now()
                .plus(props.getAccessTokenExpiration(), ChronoUnit.HOURS);
        return buildToken(claims, validity);
    }

    public String createRefreshToken(Long userId, String username) {
        initKeysIfNecessary();
        Claims claims = Jwts.claims()
                .subject(username)
                .add("id", String.valueOf(userId))
                .build();
        Instant validity = Instant.now()
                .plus(props.getRefreshTokenExpiration(), ChronoUnit.DAYS);
        String refreshToken = buildToken(claims, validity);
        String redisKey = buildRefreshKey(userId);
        redisTemplate.opsForValue().set(redisKey, refreshToken, props.getRefreshTokenExpiration(), TimeUnit.DAYS);
        return refreshToken;
    }

    public JwtResponse refreshUserTokens(String refreshToken) {
        initKeysIfNecessary();
        if (JwtUtils.isInvalid(refreshToken, publicKey)) {
            throw new AccessDeniedException("Invalid refresh token");
        }
        Long userId = Long.valueOf(JwtUtils.getId(refreshToken, publicKey));
        String redisKey = buildRefreshKey(userId);

        String storedToken = redisTemplate.opsForValue().get(redisKey);
        if (storedToken == null || !storedToken.equals(refreshToken)) {
            throw new AccessDeniedException("Refresh token is invalid or expired");
        }
        UserResponse user = userClient.getUserById(userId).getBody();
        return new JwtResponse(
                createAccessToken(userId, user.username(), user.role()),
                createRefreshToken(userId, user.username()));
    }

    public void logout(String accessToken) {
        initKeysIfNecessary();
        if (JwtUtils.isInvalid(accessToken, publicKey)) {
            throw new AccessDeniedException("Invalid refresh token");
        }
        Long userId = Long.valueOf(JwtUtils.getId(accessToken, publicKey));
        String refreshKey = buildRefreshKey(userId);
        redisTemplate.delete(refreshKey);

        long ttl = JwtUtils.getExpirationMillis(accessToken, publicKey);

        String blacklistAccessKey = buildBlacklistKey(accessToken);
        redisTemplate.opsForValue().set(blacklistAccessKey, "removed", ttl, TimeUnit.MILLISECONDS);
    }

    private String buildToken(Claims claims, Instant validity) {
        return Jwts.builder()
                .claims(claims)
                .expiration(Date.from(validity))
                .signWith(privateKey)
                .compact();
    }

    private String buildRefreshKey(Long userId) {
        return "auth:refresh:" + userId;
    }

    private String buildBlacklistKey(String accessToken) {
        return "auth:blacklist:" + DigestUtils.sha256Hex(accessToken);
    }
}
