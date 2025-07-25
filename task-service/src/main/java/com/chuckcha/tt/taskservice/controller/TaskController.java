package com.chuckcha.tt.taskservice.controller;

import com.chuckcha.tt.core.task.TaskCreationRequest;
import com.chuckcha.tt.core.task.TaskResponse;
import com.chuckcha.tt.taskservice.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllUserTasks(@RequestHeader("x-user-id") String userId,
                                                          @RequestHeader("x-username") String username) {
        return ResponseEntity.ok().body(taskService.getUserTasks(Long.parseLong(userId), username));
    }

    @PostMapping("/create")
    public ResponseEntity<TaskResponse> createTask(
            @RequestHeader("x-user-id") String userId,
            @RequestHeader("x-username") String username,
            @RequestBody TaskCreationRequest creationRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(
                Long.parseLong(userId), username, creationRequest));
    }
}
