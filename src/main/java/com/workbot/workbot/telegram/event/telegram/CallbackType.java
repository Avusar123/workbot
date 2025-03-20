package com.workbot.workbot.telegram.event.telegram;

public enum CallbackType {
    NEXT_PAGE("NTPG"),

    PREV_PAGE("PVPG"),

    SHOW_SUB("SHSB"),

    CREATE_SUB("NWSB");

    private final String serializedText;

    CallbackType(String serializedText) {
        this.serializedText = serializedText;
    }

    public String getSerializedText() {
        return serializedText;
    }
}
