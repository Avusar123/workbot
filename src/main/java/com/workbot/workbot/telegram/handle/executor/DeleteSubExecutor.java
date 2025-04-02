package com.workbot.workbot.telegram.handle.executor;

import com.workbot.workbot.telegram.handle.handler.DeleteSubHandler;
import com.workbot.workbot.telegram.setup.intent.CallbackUpdateIntent;
import com.workbot.workbot.telegram.setup.intent.type.CallbackType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class DeleteSubExecutor implements HandlerExecutor<CallbackUpdateIntent> {
    @Autowired
    private DeleteSubHandler deleteSubHandler;

    @Override
    public boolean supports(CallbackUpdateIntent intent) {
        return intent.getType() == CallbackType.DELETE_SUB;
    }

    @Override
    public void execute(CallbackUpdateIntent intent) throws TelegramApiException {
        deleteSubHandler.delete(Integer.parseInt(intent.getArgs()), intent.getUserId(), intent.getMessageId());
    }

    @Override
    public Class<CallbackUpdateIntent> getIntentType() {
        return CallbackUpdateIntent.class;
    }
}
