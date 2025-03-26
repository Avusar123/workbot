package com.workbot.workbot.telegram.newapi.processor.processor;

import com.workbot.workbot.telegram.newapi.context.UpdateContextHolder;
import com.workbot.workbot.telegram.newapi.context.resolver.HandlerEntrypoint;
import com.workbot.workbot.telegram.newapi.context.resolver.type.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageProcessor {
    @Autowired
    private UpdateContextHolder holder;

    @Autowired
    private HandlerEntrypoint handlerEntrypoint;

    public boolean isMessage() {
        return holder.getCurrent().getUpdate().hasMessage();
    }

    public void process() {
        var updateContext = holder.getCurrent();

        handlerEntrypoint.handle(
                updateContext
                        .getModelContext()
                        .getHandler(),
                State.MODEL_UPDATE,
                updateContext
                        .getModelContext()
                        .getAction());
    }
}
