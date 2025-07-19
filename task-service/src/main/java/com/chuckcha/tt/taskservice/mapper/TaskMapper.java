package com.chuckcha.tt.taskservice.mapper;

import com.chuckcha.tt.core.task.TaskCreationRequest;
import com.chuckcha.tt.core.task.TaskResponse;
import com.chuckcha.tt.taskservice.entity.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public Task toEntity(Long userId, TaskCreationRequest creationRequest) {
    return Task.builder()
            .title(creationRequest.title())
            .description(creationRequest.description())
            .deadline(creationRequest.deadline())
            .build();
    }

    public TaskResponse toResponse(String username, Task task) {
        return new TaskResponse(username, task.getId(), task.getTitle(), task.getDescription(), task.getStatus(), task.getDeadline());
    }
}
