package com.chuckcha.tt.core.mapper;

import com.chuckcha.tt.core.user.RegistrationRequest;
import com.chuckcha.tt.core.user.UserCreationRequest;
import org.mapstruct.Mapper;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Component;

public class RegistrationMapper {

    public UserCreationRequest toUserCreationRequest(RegistrationRequest registrationRequest) {
        return new UserCreationRequest(registrationRequest.username(), registrationRequest.password(), registrationRequest.email());
    }
}
