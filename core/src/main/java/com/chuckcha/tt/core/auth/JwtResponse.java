package com.chuckcha.tt.core.auth;

public record JwtResponse(Long id, String username, String accessToken, String refreshToken) {
}
