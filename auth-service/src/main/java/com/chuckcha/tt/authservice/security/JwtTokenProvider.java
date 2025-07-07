package com.chuckcha.tt.authservice.security;

import com.chuckcha.tt.authservice.config.JwtProperties;
import com.chuckcha.tt.authservice.service.AuthService;
import com.chuckcha.tt.core.auth.JwtResponse;
import com.chuckcha.tt.core.user.Role;
import com.chuckcha.tt.core.user.UserResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties props;
    private final UserDetailsService userDetailsService;
    private final AuthService authService;
    private RSAPrivateKey privateKey;

    @PostConstruct
    public void init() {
        this.privateKey = loadPrivateKey(props.getPrivateKeyPath());
    }

    public String createAccessToken(Long userId, String username, Role role) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("id", userId);
        claims.put("roles", role.getAuthority());
        return buildToken(claims, props.getAccessTokenExpiration());
    }

    public String createRefreshToken(Long userId, String username) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("id", userId);
        return buildToken(claims, props.getRefreshTokenExpiration());
    }

    public JwtResponse refreshUserTokens(String refreshToken) {

    }

    private String buildToken(Claims claims, Long tokenExpiration) {
        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime() + tokenExpiration);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(key)
                .compact();
    }

    private RSAPrivateKey loadPrivateKey(String path) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(path)) {
            String key = new String(is.readAllBytes(), StandardCharsets.UTF_8)
                    .replaceAll("-----BEGIN PRIVATE KEY-----", "")
                    .replaceAll("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s", "");

            byte[] decoded = Base64.getDecoder().decode(key);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey) keyFactory.generatePrivate(spec);

        } catch (Exception e) {
            throw new RuntimeException("Could not load private key", e);
        }
}
