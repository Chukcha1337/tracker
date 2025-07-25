package com.chuckcha.tt.userservice.repository;

import com.chuckcha.tt.outbox.entity.user.OutboxUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboxUserRepository extends JpaRepository<OutboxUser, Long> {
}
