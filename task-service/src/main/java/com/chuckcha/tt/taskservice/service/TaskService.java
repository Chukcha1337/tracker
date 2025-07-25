package com.chuckcha.tt.taskservice.service;

import com.chuckcha.tt.core.task.TaskCreationRequest;
import com.chuckcha.tt.core.task.TaskResponse;
import com.chuckcha.tt.outbox.dto.UserNameEmailResponse;

import java.util.List;

public interface TaskService {

    TaskResponse createTask(Long userId, String username, TaskCreationRequest request);
    List<TaskResponse> getUserTasks(Long userId, String username);
    List<TaskResponse> getTasksToSend(Long userId, String username);
    void prepareTasks(List<UserNameEmailResponse> users);
}
