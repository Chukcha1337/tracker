package com.chuckcha.tt.authservice.service;

import com.chuckcha.tt.authservice.entity.SecurityUser;
import com.chuckcha.tt.core.exception.UserRegistrationException;
import com.chuckcha.tt.authservice.feign.UserClient;
import com.chuckcha.tt.authservice.security.JwtTokenProvider;
import com.chuckcha.tt.core.auth.JwtRequest;
import com.chuckcha.tt.core.auth.JwtResponse;
import com.chuckcha.tt.core.mapper.RegistrationMapper;
import com.chuckcha.tt.core.user.LoginRequest;
import com.chuckcha.tt.core.user.RegistrationRequest;
import com.chuckcha.tt.core.user.Role;
import com.chuckcha.tt.core.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService, UserDetailsService {

    private final UserClient userClient;
    private final RegistrationMapper mapper;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public JwtResponse register(RegistrationRequest registrationRequest) {
        ResponseEntity<UserResponse> response = userClient.createUser(mapper.toUserCreationRequest(registrationRequest));
        if (response.getStatusCode().is2xxSuccessful() && response.hasBody()) {
            UserResponse user = response.getBody();
            String accessToken = jwtTokenProvider.createAccessToken(user.id(), user.username(), user.role());
            String refreshToken = jwtTokenProvider.createRefreshToken(user.id(), user.username());

            return new JwtResponse(user.id(), user.username(), accessToken, refreshToken);
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
        return new JwtResponse(userId, username, accessToken, refreshToken);
    }

    @Override
    public JwtResponse refresh(String refreshToken) {
        return jwtTokenProvider.refreshUserTokens(refreshToken);
    }

    @Override
    public void logout(String token) {
        jwtTokenProvider.logout(token);
    }

    @Override
    public SecurityUser loadUserByUsername(String username) throws UsernameNotFoundException {
        UserResponse userResponse = userClient.getUserByUsername(username).getBody();
        return SecurityUser.from(userResponse);
    }
}
