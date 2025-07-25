package com.chuckcha.tt.schedulerservice.repository;

import com.chuckcha.tt.outbox.entity.task.OutboxTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutboxTaskRepository extends JpaRepository<OutboxTask, Long> {
    List<OutboxTask> findByProcessedFalse();

}
