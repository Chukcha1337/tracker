package com.chuckcha.tt.outbox.entity.task;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "outbox_tasks", schema = "tasks_schema")
public class OutboxTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String username;
    private String email;

    private String serializedTasksJson;

    private Date createdAt;
    private Boolean processed;
}
