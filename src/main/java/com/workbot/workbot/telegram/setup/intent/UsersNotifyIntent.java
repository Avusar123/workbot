package com.workbot.workbot.telegram.setup.intent;

import java.util.Set;

public class UsersNotifyIntent extends UpdateIntent {
    private final Set<Long> users;

    private final String message;

    public UsersNotifyIntent(Set<Long> users, String message) {
        this.users = users;
        this.message = message;
    }

    public Set<Long> getUsers() {
        return users;
    }

    public String getMessage() {
        return message;
    }
}
