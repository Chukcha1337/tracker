package com.chuckcha.tt.authservice.controller;

import com.chuckcha.tt.authservice.feign.UserClient;
import com.chuckcha.tt.authservice.service.AuthService;
import com.chuckcha.tt.core.auth.JwtResponse;
import com.chuckcha.tt.core.user.LoginRequest;
import com.chuckcha.tt.core.user.RegistrationRequest;
import com.chuckcha.tt.core.user.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(@RequestBody @Valid RegistrationRequest registrationRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(registrationRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(@RequestBody @Valid String refreshToken) {
        return ResponseEntity.ok(authService.refresh(refreshToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        authService.logout(token);
        return ResponseEntity.ok().build();
    }
}
