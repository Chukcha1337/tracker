package com.chuckcha.tt.authservice.service;

import com.chuckcha.tt.core.auth.JwtRequest;
import com.chuckcha.tt.core.auth.JwtResponse;
import com.chuckcha.tt.core.user.RegistrationRequest;

public interface AuthService {

    JwtResponse register(RegistrationRequest registrationRequest);

    JwtResponse login(JwtRequest jwtRequest);

    JwtResponse refresh(String refreshToken);





}
