package com.chuckcha.tt.taskservice.service;

import com.chuckcha.tt.core.task.Status;
import com.chuckcha.tt.core.task.TaskCreationRequest;
import com.chuckcha.tt.core.task.TaskResponse;
import com.chuckcha.tt.outbox.dto.UserNameEmailResponse;
import com.chuckcha.tt.outbox.entity.task.OutboxTask;
import com.chuckcha.tt.taskservice.entity.Task;
import com.chuckcha.tt.taskservice.exception.TaskSerializationException;
import com.chuckcha.tt.taskservice.mapper.TaskMapper;
import com.chuckcha.tt.taskservice.repository.OutboxTaskRepository;
import com.chuckcha.tt.taskservice.repository.TaskRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final OutboxTaskRepository outboxTaskRepository;

    public TaskResponse createTask(Long userId, String username, TaskCreationRequest request) {
        Task task = taskMapper.toEntity(userId, request);
        return taskMapper.toResponse(username, taskRepository.save(task));
    }

    @Transactional
    public void prepareTasks(List<UserNameEmailResponse> users) {
        users.forEach(user -> {
            List<Task> tasks = taskRepository.findTop5ByUserIdAndStatusOrderByCreatedDateDesc(user.id(), Status.CREATED);
            if (!tasks.isEmpty()) {
                try {
                    OutboxTask outboxTask = taskMapper.toOutbox(user, tasks);
                    outboxTaskRepository.save(outboxTask);
                } catch (JsonProcessingException e) {
                    throw new TaskSerializationException("Could not serialize tasks", e);
                }
            }
        });
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> getUserTasks(Long userId, String username) {
        return taskRepository.findAllByUserId(userId).stream().map(task -> taskMapper.toResponse(username, task)).toList();
    }

    @Override
    public List<TaskResponse> getTasksToSend(Long userId, String username) {
        return taskRepository.findTop5ByUserIdOrderByCreatedDateDesc(userId).stream().map(task -> taskMapper.toResponse(username, task)).toList();
    }
}
