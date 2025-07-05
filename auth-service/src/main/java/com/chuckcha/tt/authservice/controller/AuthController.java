package com.chuckcha.tt.authservice.controller;

import com.chuckcha.tt.authservice.feign.UserClient;
import com.chuckcha.tt.authservice.service.AuthService;
import com.chuckcha.tt.core.user.LoginRequest;
import com.chuckcha.tt.core.user.RegistrationRequest;
import com.chuckcha.tt.core.user.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        return null;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid RegistrationRequest registrationRequest) {
        authService.register(registrationRequest);
        return null;
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.noContent().build();
    }



}
