package com.workbot.workbot.data.model;

public enum Company {
    BEELINE("Билайн"),
    TINKOFF("Т-Банк"),
    CINIMEX("Синимекс"),
    CROK("КРОК"),
    MTC("МТС"),
    SBER("Сбер");

    private final String displayName;

    Company(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
