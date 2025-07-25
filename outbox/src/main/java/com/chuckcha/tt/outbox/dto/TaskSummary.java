package com.chuckcha.tt.outbox.dto;

import java.time.LocalDateTime;

public record TaskSummary(String title, String description, LocalDateTime deadline) {
}
