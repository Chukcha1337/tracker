package com.chuckcha.tt.userservice.mapper;

import com.chuckcha.tt.core.user.UserCreationRequest;
import com.chuckcha.tt.core.user.UserResponse;
import com.chuckcha.tt.userservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toResponse(User user);

    @Mapping(target = "role", constant = "ROLE_USER")
    @Mapping(target = "id", ignore = true)
    User toEntity(UserCreationRequest userCreationRequest);
}
