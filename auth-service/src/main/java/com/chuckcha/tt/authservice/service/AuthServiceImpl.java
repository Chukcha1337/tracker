package com.chuckcha.tt.authservice.service;

import com.chuckcha.tt.authservice.entity.SecurityUser;
import com.chuckcha.tt.authservice.feign.UserClient;
import com.chuckcha.tt.core.auth.JwtRequest;
import com.chuckcha.tt.core.auth.JwtResponse;
import com.chuckcha.tt.core.mapper.RegistrationMapper;
import com.chuckcha.tt.core.user.RegistrationRequest;
import com.chuckcha.tt.core.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService, UserDetailsService {

    private final UserClient userClient;
    private final RegistrationMapper mapper;
    private final JwtServiceImpl jwtService;

    @Override
    public JwtResponse register(RegistrationRequest registrationRequest) {
        ResponseEntity<UserResponse> response = userClient.createUser(mapper.toUserCreationRequest(registrationRequest));
        if (response.getStatusCode().is2xxSuccessful() && response.hasBody()) {
            UserResponse user = response.getBody();
            String accessToken = jwtService.generateToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);
            return new JwtResponse(user.id(), user.username(), accessToken, refreshToken);
        }
        return null;
    }

    @Override
    public JwtResponse login(JwtRequest jwtRequest) {
        return null;
    }

    @Override
    public JwtResponse refresh(String refreshToken) {
        return null;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserResponse userResponse = userClient.getUserByUsername(username).getBody();
        return SecurityUser.from(userResponse);
    }
}
