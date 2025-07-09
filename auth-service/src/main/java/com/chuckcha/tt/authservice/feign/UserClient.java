package com.chuckcha.tt.authservice.feign;


import com.chuckcha.tt.core.user.RegistrationRequest;
import com.chuckcha.tt.core.user.UserCreationRequest;
import com.chuckcha.tt.core.user.UserResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@FeignClient(name = "user-service", path = "/internal/users")
public interface UserClient {

    @PostMapping()
    ResponseEntity<UserResponse> createUser(@RequestBody UserCreationRequest userCreationRequest);

    @GetMapping("/by-username/{username}")
    ResponseEntity<UserResponse> getUserByUsername(@PathVariable("username") @NotBlank @NotNull String username);

    @GetMapping("/by-id/{id}")
    ResponseEntity<UserResponse> getUserById(@PathVariable("id") @NotBlank @NotNull Long id);
}
