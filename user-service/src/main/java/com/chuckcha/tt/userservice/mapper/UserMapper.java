package com.chuckcha.tt.userservice.mapper;

import com.chuckcha.tt.core.user.Role;
import com.chuckcha.tt.core.user.SecurityUserResponse;
import com.chuckcha.tt.core.user.UserCreationRequest;
import com.chuckcha.tt.core.user.UserEmailResponse;
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
}
