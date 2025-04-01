package com.workbot.workbot.telegram.setup.intent;

import com.workbot.workbot.telegram.setup.intent.type.CallbackType;

public class CallbackUpdateIntent extends MessageUpdateIntent {
    private final CallbackType type;

    private final String args;

    public CallbackUpdateIntent(int messageId,
                                long userId, CallbackType type, String args) {
        super(messageId, userId);
        this.type = type;
        this.args = args;
    }

    public String getArgs() {
        return args;
    }

    public CallbackType getType() {
        return type;
    }
}
