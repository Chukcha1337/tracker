package com.chuckcha.tt.core.task;

import java.time.LocalDateTime;

public record TaskCreationRequest(String title, String description, LocalDateTime deadline) {
}
