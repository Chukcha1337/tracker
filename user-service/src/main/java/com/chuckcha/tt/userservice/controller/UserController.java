package com.chuckcha.tt.userservice.controller;

import com.chuckcha.tt.core.user.UserEmailResponse;
import com.chuckcha.tt.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserEmailResponse> getMe(@RequestHeader("x-user-id") String userId) {
        return ResponseEntity.ok().body(userService.getUserEmailById(Long.parseLong(userId)));
    }
}
