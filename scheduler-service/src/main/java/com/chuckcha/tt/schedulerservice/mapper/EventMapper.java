package com.chuckcha.tt.schedulerservice.mapper;

import com.chuckcha.tt.core.event.EmailSendingEvent;
import com.chuckcha.tt.outbox.dto.TaskSummary;
import com.chuckcha.tt.outbox.entity.task.OutboxTask;
import com.chuckcha.tt.outbox.entity.user.OutboxUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EventMapper {

    private final ObjectMapper objectMapper;

    public EmailSendingEvent toEvent(OutboxUser outboxUser) {
        return new EmailSendingEvent(
                outboxUser.getEmail(),
                outboxUser.getUsername(),
                "New user creation",
                """
                        Dear %s,
                        We are pleasured to welcome you at Task Tracker Service.
                        Your account was successfully created!
                        """.formatted(outboxUser.getUsername()));
    }

    public EmailSendingEvent toEvent(OutboxTask outboxTask) {
        List<TaskSummary> summary;
        try {
            summary = List.of(objectMapper.readValue(outboxTask.getSerializedTasksJson(), TaskSummary[].class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize task summary", e);
        }
        StringBuilder taskText = new StringBuilder();
        for (int i = 0; i < summary.size(); i++) {
            TaskSummary task = summary.get(i);
            taskText.append(i + 1)
                    .append(") Title: ")
                    .append(task.title())
                    .append(" Description: ")
                    .append(task.description());
            if (task.deadline() != null) {
                taskText.append(" ").append(task.deadline());
            }
            taskText.append("\n");
        }
        return new EmailSendingEvent(
                outboxTask.getEmail(),
                outboxTask.getUsername(),
                "Tasks in progress",
                """
                        Dear %s,
                        There are some tasks in progress.
                        Here are the tasks that were submitted and in progress:
                        %s
                        """.formatted(outboxTask.getUsername(), taskText.toString()));
    }

}
