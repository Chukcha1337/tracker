package com.chuckcha.tt.schedulerservice.feign;

import com.chuckcha.tt.outbox.dto.UserNameEmailResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(
        name = "user-service",
        path = "/internal/users"
)
public interface UserClient {

    @GetMapping("/all")
    ResponseEntity<List<UserNameEmailResponse>> getAllUserResponses();
}

