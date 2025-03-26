package com.workbot.workbot.telegram.newapi.context.resolver;

import com.workbot.workbot.telegram.newapi.context.resolver.type.Action;
import com.workbot.workbot.telegram.newapi.context.resolver.type.State;
import com.workbot.workbot.telegram.newapi.context.resolver.type.Handler;

public interface HandlerExecutor {
    Handler getHandlerType();

    State getState();

    Action getAction();

    void execute();
}
