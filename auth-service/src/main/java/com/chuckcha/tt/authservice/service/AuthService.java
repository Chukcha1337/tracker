package com.chuckcha.tt.authservice.service;

import com.chuckcha.tt.core.auth.JwtRequest;
import com.chuckcha.tt.core.auth.JwtResponse;
import com.chuckcha.tt.core.user.LoginRequest;
import com.chuckcha.tt.core.user.RegistrationRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    JwtResponse register(RegistrationRequest registrationRequest);

    JwtResponse login(LoginRequest loginRequest);

    JwtResponse refresh(String refreshToken);

    void logout(String token);
}
