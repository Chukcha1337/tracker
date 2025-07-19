package com.chuckcha.tt.core.user;

public record SecurityUserResponse(Long id, String username, String password, Role role) {
}
