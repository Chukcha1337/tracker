package com.chuckcha.tt.core.task;

import java.time.LocalDateTime;

public record TaskResponse(String username, Long id, String title, String description, Status status, LocalDateTime deadline) {
}
