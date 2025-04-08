package com.workbot.workbot.logic.update.section.util;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UpdateStatusHolder {
    private boolean processing = false;

    private Set<Long> waitingUsers = new HashSet<>();

    public boolean isProcessing() {
        return processing;
    }

    public void setProcessing(boolean processing) {
        this.processing = processing;
    }

    public void addUser(long userId) {
        waitingUsers.add(userId);
    }

    public void flushUsers() {
        waitingUsers.clear();
    }

    public Set<Long> getWaitingUsers() {
        return waitingUsers;
    }
}
