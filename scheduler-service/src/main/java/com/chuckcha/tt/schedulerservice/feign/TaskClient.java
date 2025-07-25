package com.chuckcha.tt.schedulerservice.feign;

import com.chuckcha.tt.outbox.dto.UserNameEmailResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        name = "task-service",
        path = "/internal/tasks"
)
public interface TaskClient {

    @PostMapping("/prepare")
    ResponseEntity<Void> prepareUsersTasks(@RequestBody List<UserNameEmailResponse> users);
}
