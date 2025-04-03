package com.workbot.workbot.data.model.dto.util;

import java.util.Objects;

public class TelegramSafeString {
    private final String content;

    public TelegramSafeString(String content) {
        this.content = content;
    }

    public String getUnsafe() {
        return content;
    }

    public String getFormatted() {
        return escapeMarkdownV2(content);
    }

    public static String escapeMarkdownV2(String text) {
        return text.replaceAll("([_\\*\\[\\]()~`>#+\\-=|{}.!])", "\\\\$1");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o instanceof String s) {
            return Objects.equals(content, s);
        }

        if (o instanceof TelegramSafeString s) {
            return Objects.equals(this.content, s.content);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(content);
    }

    @Override
    public String toString() {
        return getFormatted();
    }
}
