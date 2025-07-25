package com.chuckcha.tt.core.event;

public record EmailSendingEvent(String email, String username, String title, String description) {
}
