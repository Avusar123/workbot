package com.workbot.workbot.telegram.newapi.setup.intent;

public abstract class MessageUpdateIntent extends UpdateIntent {
    protected final int messageId;

    protected final long userId;

    public MessageUpdateIntent(int messageId, long userId) {
        this.messageId = messageId;
        this.userId = userId;
    }

    public int getMessageId() {
        return messageId;
    }

    public long getUserId() {
        return userId;
    }
}
