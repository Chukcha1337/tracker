package com.chuckcha.tt.taskservice.service;

import com.chuckcha.tt.core.task.TaskCreationRequest;
import com.chuckcha.tt.core.task.TaskResponse;
import com.chuckcha.tt.taskservice.entity.Task;
import com.chuckcha.tt.taskservice.mapper.TaskMapper;
import com.chuckcha.tt.taskservice.repository.TaskRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskResponse createTask(Long userId, String username, TaskCreationRequest request) {
        Task task = taskMapper.toEntity(userId, request);
        return taskMapper.toResponse(username, taskRepository.save(task));
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
