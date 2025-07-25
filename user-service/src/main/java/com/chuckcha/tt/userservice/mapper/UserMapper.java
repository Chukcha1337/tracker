package com.chuckcha.tt.userservice.mapper;

import com.chuckcha.tt.core.user.*;
import com.chuckcha.tt.outbox.dto.UserNameEmailResponse;
import com.chuckcha.tt.userservice.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public SecurityUserResponse toResponse(User user) {
        return new SecurityUserResponse(user.getId(), user.getUsername(), user.getPassword(), user.getRole());
    }

    public User toEntity(UserCreationRequest userCreationRequest) {
        return User.builder()
                .username(userCreationRequest.username())
                .password(userCreationRequest.password())
                .email(userCreationRequest.email())
                .role(Role.USER)
                .build();
    }


    public UserEmailResponse toEmailResponse(User user) {
        return new UserEmailResponse(user.getId(), user.getEmail());
    }

    public UserNameEmailResponse toNameEmailResponse(User user) {
        return new UserNameEmailResponse(user.getId(), user.getEmail(), user.getUsername());
    }
}
