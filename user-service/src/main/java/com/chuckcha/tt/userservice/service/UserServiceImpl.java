package com.chuckcha.tt.userservice.service;

import com.chuckcha.tt.core.user.RegistrationRequest;
import com.chuckcha.tt.core.user.UserCreationRequest;
import com.chuckcha.tt.core.user.UserResponse;
import com.chuckcha.tt.userservice.entity.User;
import com.chuckcha.tt.userservice.exception.UserAlreadyExistsException;
import com.chuckcha.tt.userservice.mapper.UserMapper;
import com.chuckcha.tt.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.zip.DataFormatException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponse getUser(RegistrationRequest userRequest) {
        return null;
    }

    @Transactional
    public UserResponse createUser(UserCreationRequest request) {
        User user = userMapper.toEntity(request);
        try {
            return userMapper.toResponse(userRepository.save(user));
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExistsException("User with this credentials already exists");
        }
    }

    public void deleteUser(String userId) {

    }
//    public Optional<UserResponse> findByUsername(String username) {
//        return userRepository.findByUsername(username)
//                .map(userMapper::toResponse)
//                .orElseThrow(DataFormatException::new);
//    }
}
