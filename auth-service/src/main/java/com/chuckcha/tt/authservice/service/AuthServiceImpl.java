package com.chuckcha.tt.authservice.service;

import com.chuckcha.tt.authservice.entity.SecurityUser;
import com.chuckcha.tt.core.exception.UserRegistrationException;
import com.chuckcha.tt.authservice.feign.UserClient;
import com.chuckcha.tt.authservice.security.JwtTokenProvider;
import com.chuckcha.tt.core.auth.JwtResponse;
import com.chuckcha.tt.core.mapper.RegistrationMapper;
import com.chuckcha.tt.core.user.LoginRequest;
import com.chuckcha.tt.core.user.RegistrationRequest;
import com.chuckcha.tt.core.user.Role;
import com.chuckcha.tt.core.user.SecurityUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserClient userClient;
    private final RegistrationMapper mapper;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public JwtResponse register(RegistrationRequest registrationRequest) {
        ResponseEntity<SecurityUserResponse> response = userClient.createUser(mapper.toUserCreationRequest(registrationRequest));
        if (response.getStatusCode().is2xxSuccessful() && response.hasBody()) {
            SecurityUserResponse user = response.getBody();
            String accessToken = jwtTokenProvider.createAccessToken(user.id(), user.username(), user.role());
            String refreshToken = jwtTokenProvider.createRefreshToken(user.id(), user.username());

            return new JwtResponse(accessToken, refreshToken);
        } else {
            throw new UserRegistrationException(
                    "User [%s] couldn't be registered".formatted(registrationRequest.username()));
        }
    }

    @Override
    public JwtResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        Long userId = securityUser.getId();
        String username = securityUser.getUsername();
        Role role = (Role) securityUser.getAuthorities().stream().findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "This authorities " + securityUser.getAuthorities().toString() + " are not supported"));

        String accessToken = jwtTokenProvider.createAccessToken(userId, username, role);
        String refreshToken = jwtTokenProvider.createRefreshToken(userId, username);
        return new JwtResponse(accessToken, refreshToken);
    }

    @Override
    public JwtResponse refresh(String refreshToken) {
        return jwtTokenProvider.refreshUserTokens(refreshToken);
    }

    @Override
    public void logout(String token) {
        jwtTokenProvider.logout(token);
    }


}
