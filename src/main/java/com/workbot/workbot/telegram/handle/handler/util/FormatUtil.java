package com.workbot.workbot.telegram.handle.handler.util;

public final class FormatUtil {
    private FormatUtil() {}

    public static String escapeMarkdownV2(String text) {
        return text.replaceAll("([_\\*\\[\\]()~`>#+\\-=|{}.!])", "\\\\$1");
    }
}
