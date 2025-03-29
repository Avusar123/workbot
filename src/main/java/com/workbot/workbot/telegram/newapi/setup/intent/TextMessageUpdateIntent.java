package com.workbot.workbot.telegram.newapi.setup.intent;

public class TextMessageUpdateIntent extends MessageUpdateIntent {
    private final String text;

    public TextMessageUpdateIntent(int messageId, String text, long userId) {
        super(messageId, userId);
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
