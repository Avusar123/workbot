package com.workbot.workbot.data.model;


import com.workbot.workbot.data.model.dto.util.TelegramSafeString;

public enum Area {
    IT("IT");

    private final String displayName;

    Area(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return TelegramSafeString.escapeMarkdownV2(displayName);
    }
}
