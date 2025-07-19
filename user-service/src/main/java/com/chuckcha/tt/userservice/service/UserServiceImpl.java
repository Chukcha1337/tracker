package com.chuckcha.tt.userservice.service;

import com.chuckcha.tt.core.user.SecurityUserResponse;
import com.chuckcha.tt.core.user.UserCreationRequest;
import com.chuckcha.tt.core.user.UserEmailResponse;
import com.chuckcha.tt.userservice.entity.User;
import com.chuckcha.tt.userservice.exception.UserAlreadyExistsException;
import com.chuckcha.tt.userservice.exception.UserNotFoundException;
import com.chuckcha.tt.userservice.mapper.UserMapper;
import com.chuckcha.tt.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public SecurityUserResponse createUser(UserCreationRequest request) {
        User user = userMapper.toEntity(request);
        try {
            return userMapper.toResponse(userRepository.save(user));
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExistsException("User with this credentials already exists");
        }
    }

    public void deleteUser(Long userId) {

    }

    public SecurityUserResponse getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new UserNotFoundException("User with this credentials does not exist"));
    }


    public SecurityUserResponse getUserById(Long id) {
            return userRepository.findById(id)
                    .map(userMapper::toResponse)
                    .orElseThrow(() -> new UserNotFoundException("User with this credentials does not exist"));
    }

    @Override
    public UserEmailResponse getUserEmailById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toEmailResponse)
                .orElseThrow(() -> new UserNotFoundException("User with this credentials does not exist"));
    }
}
