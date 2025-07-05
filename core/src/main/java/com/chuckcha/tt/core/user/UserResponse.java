package com.chuckcha.tt.core.user;

public record UserResponse(Long id, String username, String password, Role role) {
}
