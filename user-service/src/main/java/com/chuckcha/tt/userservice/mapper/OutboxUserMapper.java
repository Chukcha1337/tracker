package com.chuckcha.tt.userservice.mapper;

import com.chuckcha.tt.outbox.entity.user.OutboxUser;
import com.chuckcha.tt.userservice.entity.User;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class OutboxUserMapper {

    public OutboxUser toOutbox(User user) {
        return OutboxUser.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .createdAt(new Date())
                .processed(false)
                .build();

    }
}
