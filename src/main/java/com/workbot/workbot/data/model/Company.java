package com.workbot.workbot.data.model;

public enum Company {
    BEELINE("Билайн");

    private final String displayName;

    Company(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
