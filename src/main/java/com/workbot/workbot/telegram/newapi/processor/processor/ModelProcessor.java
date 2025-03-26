package com.workbot.workbot.telegram.newapi.processor.processor;

import com.workbot.workbot.telegram.newapi.context.UpdateContextHolder;
import com.workbot.workbot.telegram.newapi.context.resolver.HandlerEntrypoint;
import com.workbot.workbot.telegram.newapi.context.resolver.type.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ModelProcessor {
    @Autowired
    private UpdateContextHolder holder;

    @Autowired
    private HandlerEntrypoint handlerEntrypoint;

    public boolean isModel() {
        var updateContext = holder.getCurrent();

        return updateContext.getUpdate().hasCallbackQuery() && updateContext.hasModel();
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
