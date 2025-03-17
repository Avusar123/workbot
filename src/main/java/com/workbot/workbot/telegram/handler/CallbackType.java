package com.workbot.workbot.telegram.handler;

public enum CallbackType {
    SHOW_SUB("SHSB"),
    NEW_SUB("NS");

    private final String serialized;

    CallbackType(String serialized) {
        this.serialized = serialized;
    }

    public String getSerialized() {
        return serialized;
    }
}