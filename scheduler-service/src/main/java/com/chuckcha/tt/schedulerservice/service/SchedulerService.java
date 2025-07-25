package com.chuckcha.tt.schedulerservice.service;

import com.chuckcha.tt.core.event.EmailSendingEvent;
import com.chuckcha.tt.outbox.dto.UserNameEmailResponse;
import com.chuckcha.tt.outbox.entity.task.OutboxTask;
import com.chuckcha.tt.outbox.entity.user.OutboxUser;
import com.chuckcha.tt.schedulerservice.exception.OutboxPollingException;
import com.chuckcha.tt.schedulerservice.exception.TasksPreparationException;
import com.chuckcha.tt.schedulerservice.feign.TaskClient;
import com.chuckcha.tt.schedulerservice.feign.UserClient;
import com.chuckcha.tt.schedulerservice.mapper.EventMapper;
import com.chuckcha.tt.schedulerservice.publisher.EventPublisher;
import com.chuckcha.tt.schedulerservice.repository.OutboxTaskRepository;
import com.chuckcha.tt.schedulerservice.repository.OutboxUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@EnableScheduling
@Slf4j
public class SchedulerService {

    private final OutboxUserRepository outboxUserRepository;
    private final OutboxTaskRepository outboxTaskRepository;
    private final EventPublisher eventPublisher;
    private final EventMapper eventMapper;
    private final UserClient userClient;
    private final TaskClient taskClient;


    @Scheduled(cron = "*/20 * * * * *")
    @Transactional("transactionManager")
    public void pollOutboxUsersAndPublish() {
        List<OutboxUser> unprocessedUsers = outboxUserRepository.findByProcessedFalse();
        log.info("Unprocessed users: {}", unprocessedUsers);
        if (!unprocessedUsers.isEmpty()) {
            unprocessedUsers.forEach(outboxUser -> {
                try {
                    EmailSendingEvent event = eventMapper.toEvent(outboxUser);
                    eventPublisher.publish(event);
                    outboxUser.setProcessed(true);
                    outboxUserRepository.save(outboxUser);
                } catch (Exception e) {
                    throw new OutboxPollingException("Could not get events from outbox", e);
                }
            });
        }
        ;
    }

    //    @Scheduled(cron = "* * 0 * * *")
    @Scheduled(cron = "*/20 * * * * *")
    @Transactional("transactionManager")
    public void pollOutboxTaskAndPublish() {
        List<UserNameEmailResponse> users = userClient.getAllUserResponses().getBody();
        ResponseEntity<Void> response = taskClient.prepareUsersTasks(users);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new TasksPreparationException("Could not prepare tasks to send");
        }
        List<OutboxTask> unprocessedTasks = outboxTaskRepository.findByProcessedFalse();
        if (!unprocessedTasks.isEmpty()) {
            unprocessedTasks.forEach(outboxTask -> {
                try {
                    EmailSendingEvent event = eventMapper.toEvent(outboxTask);
                    eventPublisher.publish(event);
                    outboxTask.setProcessed(true);
                    outboxTaskRepository.save(outboxTask);
                } catch (Exception e) {
                    throw new OutboxPollingException("Could not get events from outbox", e);
                }
            });
        }
    }


}

