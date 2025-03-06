package com.workbot.workbot.data.model;


public enum Area {
    IT("IT");

    private final String displayName;

    Area(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
