package com.chuckcha.tt.gateway.filter;

import com.chuckcha.tt.core.auth.JwtUtils;
import com.chuckcha.tt.gateway.config.AuthProperties;
import com.chuckcha.tt.gateway.config.PublicKeyLoader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter implements WebFilter {

    private final ObjectProvider<PublicKeyLoader> publicKeyLoader;
    private final ReactiveRedisTemplate<String, String> reactiveRedisTemplate;
    private final AuthProperties authProperties;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String path = request.getURI().getPath();
        if (isWhitelisted(path)) {
            return chain.filter(exchange);
        }

        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return unauthorized(exchange, "Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);

        try {
            RSAPublicKey publicKey = publicKeyLoader.getObject().getPublicKey();

            if (JwtUtils.isInvalid(token, publicKey)) {
                return unauthorized(exchange, "Token expired or invalid");
            }

            String tokenHash = DigestUtils.sha256Hex(token);
            String redisKey = "auth:blacklist:" + tokenHash;

            return reactiveRedisTemplate.hasKey(redisKey)
                    .flatMap(exists -> {
                        if (exists) {
                            return unauthorized(exchange, "Token revoked");
                        }

                        String username = JwtUtils.getSubject(token, publicKey);
                        String userId = JwtUtils.getId(token, publicKey);
                        String role = JwtUtils.getRole(token, publicKey);
                        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

                        Authentication authentication = new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                authorities
                        );
                        ServerHttpRequest mutatedRequest = request.mutate()
                                .header("x-user-id", userId)
                                .header("x-username", username)
                                .header("x-role", role)
                                .build();

                        ServerWebExchange mutatedExchange = exchange.mutate()
                                .request(mutatedRequest)
                                .build();

                        return chain.filter(mutatedExchange)
                                .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
                    });
        } catch (Exception e) {
            log.error("JWT validation error: {}", e.getMessage(), e);
            return unauthorized(exchange, "Token validation failed");
        }
    }

    private boolean isWhitelisted(String path) {
        return authProperties.getWhitelist().stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, path));
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange, String message) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        String body = "{\"error\": \"" + message + "\"}";
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
        return exchange.getResponse().writeWith(Mono.just(buffer));
    }
}