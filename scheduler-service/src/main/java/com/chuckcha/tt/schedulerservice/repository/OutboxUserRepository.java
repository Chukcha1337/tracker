package com.chuckcha.tt.schedulerservice.repository;

import com.chuckcha.tt.outbox.entity.user.OutboxUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutboxUserRepository extends JpaRepository<OutboxUser, Long> {
    List<OutboxUser> findByProcessedFalse();
}
