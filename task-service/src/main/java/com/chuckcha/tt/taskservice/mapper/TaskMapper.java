package com.chuckcha.tt.taskservice.mapper;

import com.chuckcha.tt.core.task.Status;
import com.chuckcha.tt.core.task.TaskCreationRequest;
import com.chuckcha.tt.core.task.TaskResponse;
import com.chuckcha.tt.outbox.dto.TaskSummary;
import com.chuckcha.tt.outbox.dto.UserNameEmailResponse;
import com.chuckcha.tt.outbox.entity.task.OutboxTask;
import com.chuckcha.tt.taskservice.entity.Task;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TaskMapper {

    private final ObjectMapper objectMapper;

    public Task toEntity(Long userId, TaskCreationRequest creationRequest) {
    return Task.builder()
            .userId(userId)
            .title(creationRequest.title())
            .description(creationRequest.description())
            .deadline(creationRequest.deadline())
            .status(Status.CREATED)
            .createdDate(LocalDateTime.now())
            .build();
    }

    public OutboxTask toOutbox(UserNameEmailResponse user, List<Task> tasks) throws JsonProcessingException {
        List<TaskSummary> summary = tasks.stream()
                .map(t -> new TaskSummary(t.getTitle(), t.getDescription(), t.getDeadline()))
                .toList();
        return OutboxTask.builder()
                .userId(user.id())
                .username(user.username())
                .email(user.email())
                .serializedTasksJson(objectMapper.writeValueAsString(summary))
                .createdAt(new Date())
                .processed(false)
                .build();
    }

    public TaskResponse toResponse(String username, Task task) {
        return new TaskResponse(username, task.getId(), task.getTitle(), task.getDescription(), task.getStatus(), task.getDeadline());
    }
}
