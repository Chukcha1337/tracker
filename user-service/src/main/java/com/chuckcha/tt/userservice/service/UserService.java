package com.chuckcha.tt.userservice.service;

import com.chuckcha.tt.core.user.SecurityUserResponse;
import com.chuckcha.tt.core.user.UserCreationRequest;
import com.chuckcha.tt.core.user.UserEmailResponse;
import com.chuckcha.tt.outbox.dto.UserNameEmailResponse;

import java.util.List;

public interface UserService {

    SecurityUserResponse createUser(UserCreationRequest request);
    SecurityUserResponse getUserByUsername(String username);
    SecurityUserResponse getUserById(Long id);
    UserEmailResponse getUserEmailById(Long id);
    void deleteUser(Long userId);

    List<UserNameEmailResponse> getAllUsers();
}
