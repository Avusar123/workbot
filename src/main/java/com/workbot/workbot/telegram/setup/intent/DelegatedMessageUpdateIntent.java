package com.workbot.workbot.telegram.setup.intent;

public class DelegatedMessageUpdateIntent extends MessageUpdateIntent {
    private int sourceMessageId;

    private final MessageUpdateIntent inner;

    public DelegatedMessageUpdateIntent(int sourceMessageId, MessageUpdateIntent inner) {
        super(inner.getMessageId(), inner.getUserId());
        this.sourceMessageId = sourceMessageId;
        this.inner = inner;
    }

    public int getSourceMessageId() {
        return sourceMessageId;
    }

    public void setSourceMessageId(int sourceMessageId) {
        this.sourceMessageId = sourceMessageId;
    }

    public MessageUpdateIntent getInner() {
        return inner;
    }
}
