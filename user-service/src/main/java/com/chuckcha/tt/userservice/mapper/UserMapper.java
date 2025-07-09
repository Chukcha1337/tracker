package com.chuckcha.tt.userservice.mapper;

import com.chuckcha.tt.core.user.Role;
import com.chuckcha.tt.core.user.UserCreationRequest;
import com.chuckcha.tt.core.user.UserResponse;
import com.chuckcha.tt.userservice.entity.User;
import jakarta.persistence.Column;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getPassword(), user.getRole());
    }

    public User toEntity(UserCreationRequest userCreationRequest) {
        return User.builder()
                .username(userCreationRequest.username())
                .password(userCreationRequest.password())
                .email(userCreationRequest.email())
                .role(Role.USER)
                .build();

    }
}
