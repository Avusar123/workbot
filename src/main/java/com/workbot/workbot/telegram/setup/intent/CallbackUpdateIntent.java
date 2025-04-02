package com.workbot.workbot.telegram.setup.intent;

import com.workbot.workbot.telegram.setup.intent.type.CallbackType;

public class CallbackUpdateIntent extends MessageUpdateIntent {
    private final CallbackType type;

    private final String args;

    private String queryId;

    public CallbackUpdateIntent(int messageId,
                                long userId, CallbackType type, String args, String queryId) {
        super(messageId, userId);
        this.type = type;
        this.args = args;
        this.queryId = queryId;
    }

    public String getArgs() {
        return args;
    }

    public CallbackType getType() {
        return type;
    }

    public String getQueryId() {
        return queryId;
    }
}
