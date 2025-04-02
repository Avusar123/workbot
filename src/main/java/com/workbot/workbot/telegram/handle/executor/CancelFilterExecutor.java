package com.workbot.workbot.telegram.handle.executor;

import com.workbot.workbot.telegram.handle.handler.CancelOperationHandler;
import com.workbot.workbot.telegram.setup.intent.CallbackUpdateIntent;
import com.workbot.workbot.telegram.setup.intent.type.CallbackType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class CancelFilterExecutor implements HandlerExecutor<CallbackUpdateIntent> {
    @Autowired
    private CancelOperationHandler cancelOperationHandler;

    @Override
    public boolean supports(CallbackUpdateIntent intent) {
        return intent.getType() == CallbackType.CANCEL_FILTER;
    }

    @Override
    public void execute(CallbackUpdateIntent intent) throws TelegramApiException {
        cancelOperationHandler.cancel(intent.getUserId(), intent.getMessageId());
    }

    @Override
    public Class<CallbackUpdateIntent> getIntentType() {
        return CallbackUpdateIntent.class;
    }
}
