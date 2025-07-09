package com.chuckcha.tt.userservice.service;

import com.chuckcha.tt.core.user.UserCreationRequest;
import com.chuckcha.tt.core.user.UserResponse;

public interface UserService {

    UserResponse createUser(UserCreationRequest request);
    UserResponse getUserByUsername(String username);
    UserResponse getUserById(Long id);
    void deleteUser(Long userId);
}
