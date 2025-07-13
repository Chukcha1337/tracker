package com.chuckcha.tt.userservice.controller;

import com.chuckcha.tt.core.user.UserCreationRequest;
import com.chuckcha.tt.core.user.UserResponse;
import com.chuckcha.tt.userservice.service.UserService;
import com.chuckcha.tt.userservice.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMe(@RequestHeader("x-user-id") String userId) {
        return ResponseEntity.ok().body(userService.getUserById(Long.parseLong(userId)));
    }

    @DeleteMapping("/{user-id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable("user-id") Long userId
    ) {
        userService.deleteUser(userId);
        return ResponseEntity.accepted().build();
    }
}
