package com.workbot.workbot.telegram.setup.intent;

public class AdminNotifyIntent extends UpdateIntent {
    private String message;

    public AdminNotifyIntent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
