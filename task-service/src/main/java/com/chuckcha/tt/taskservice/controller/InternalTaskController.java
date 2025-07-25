package com.chuckcha.tt.taskservice.controller;

import com.chuckcha.tt.outbox.dto.UserNameEmailResponse;
import com.chuckcha.tt.taskservice.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internal/tasks")
@RequiredArgsConstructor
public class InternalTaskController {

    private final TaskService taskService;

    @PostMapping("/prepare")
    public ResponseEntity<Void> prepareUsersTasks(@RequestBody List<UserNameEmailResponse> users) {
        taskService.prepareTasks(users);
        return ResponseEntity.ok().build();
    }
}
