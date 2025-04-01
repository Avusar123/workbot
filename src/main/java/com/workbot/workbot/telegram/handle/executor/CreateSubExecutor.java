package com.workbot.workbot.telegram.handle.executor;

import com.workbot.workbot.telegram.handle.handler.CreateSubHandler;
import com.workbot.workbot.telegram.setup.intent.CallbackUpdateIntent;
import com.workbot.workbot.telegram.setup.intent.type.CallbackType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class CreateSubExecutor implements HandlerExecutor<CallbackUpdateIntent> {
    @Autowired
    private CreateSubHandler createSubHandler;

    @Override
    public boolean supports(CallbackUpdateIntent intent) {
        return intent.getType() == CallbackType.CREATE_SUB;
    }

    @Override
    public void execute(CallbackUpdateIntent intent) throws TelegramApiException {
        createSubHandler.handle(intent);
    }

    @Override
    public Class<CallbackUpdateIntent> getIntentType() {
        return CallbackUpdateIntent.class;
    }
}
