package com.workbot.workbot.telegram.newapi.context.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.workbot.workbot.telegram.newapi.context.resolver.type.Action;
import com.workbot.workbot.telegram.newapi.context.resolver.type.Handler;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
public abstract class ModelContext {
    private Handler handler;

    public Handler getHandler() {
        return handler;
    }

    public abstract Action getAction();

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
