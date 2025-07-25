package com.chuckcha.tt.core.mapper;

import com.chuckcha.tt.core.user.RegistrationRequest;
import com.chuckcha.tt.core.user.UserCreationRequest;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
public class RegistrationMapper {

    private final PasswordEncoder passwordEncoder;

    public UserCreationRequest toUserCreationRequest(RegistrationRequest registrationRequest) {
        return new UserCreationRequest(
                registrationRequest.username(),
                passwordEncoder.encode(registrationRequest.password()),
                registrationRequest.email());
    }
}
