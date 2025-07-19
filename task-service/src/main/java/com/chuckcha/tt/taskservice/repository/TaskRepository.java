package com.chuckcha.tt.taskservice.repository;

import com.chuckcha.tt.core.task.TaskResponse;
import com.chuckcha.tt.taskservice.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.config.annotation.web.PortMapperDsl;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByUserId(Long userId);

    List<Task> findTop5ByUserIdOrderByCreatedDateDesc(Long userId);
}
