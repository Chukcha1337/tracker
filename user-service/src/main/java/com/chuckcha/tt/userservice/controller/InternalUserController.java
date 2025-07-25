package com.chuckcha.tt.userservice.controller;

import com.chuckcha.tt.core.user.UserCreationRequest;
import com.chuckcha.tt.core.user.SecurityUserResponse;
import com.chuckcha.tt.outbox.dto.UserNameEmailResponse;
import com.chuckcha.tt.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internal/users")
@RequiredArgsConstructor
public class InternalUserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<SecurityUserResponse> createUser(@RequestBody UserCreationRequest userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userRequest));
    }

    @GetMapping("/by-username/{username}")
    public ResponseEntity<SecurityUserResponse> getByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @GetMapping("/by-id/{id}")
    public ResponseEntity<SecurityUserResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserNameEmailResponse>> getAllUsersId() {
        return ResponseEntity.ok(userService.getAllUsers());
    }


}
