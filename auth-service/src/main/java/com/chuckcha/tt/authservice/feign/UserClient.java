package com.chuckcha.tt.authservice.feign;


import com.chuckcha.tt.core.user.UserCreationRequest;
import com.chuckcha.tt.core.user.SecurityUserResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "user-service",
        url = "http://localhost:8070", // Прямой URL (замените на реальный)
        path = "/internal/users"
)
public interface UserClient {

    @PostMapping()
    ResponseEntity<SecurityUserResponse> createUser(@RequestBody UserCreationRequest userCreationRequest);

    @GetMapping("/by-username/{username}")
    ResponseEntity<SecurityUserResponse> getUserByUsername(@PathVariable("username") String username);

    @GetMapping("/by-id/{id}")
    ResponseEntity<SecurityUserResponse> getUserById(@PathVariable("id") @NotBlank @NotNull Long id);
}
