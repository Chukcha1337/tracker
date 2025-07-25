package com.chuckcha.tt.taskservice.repository;

import com.chuckcha.tt.outbox.entity.task.OutboxTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboxTaskRepository extends JpaRepository<OutboxTask, Long> {
}
