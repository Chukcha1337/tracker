package com.chuckcha.tt.core.mapper;

import com.chuckcha.tt.core.user.RegistrationRequest;
import com.chuckcha.tt.core.user.UserCreationRequest;
import org.mapstruct.Mapper;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

@Mapper(componentModel = "spring")
public interface RegistrationMapper {

    UserCreationRequest toUserCreationRequest(RegistrationRequest registrationRequest);
}
