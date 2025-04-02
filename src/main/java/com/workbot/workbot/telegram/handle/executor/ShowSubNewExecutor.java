package com.workbot.workbot.telegram.handle.executor;

import com.workbot.workbot.telegram.handle.handler.ShowSubHandler;
import com.workbot.workbot.telegram.setup.intent.CallbackUpdateIntent;
import com.workbot.workbot.telegram.setup.intent.type.CallbackType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class ShowSubNewExecutor implements HandlerExecutor<CallbackUpdateIntent> {
    @Autowired
    private ShowSubHandler showSubHandler;

    @Override
    public boolean supports(CallbackUpdateIntent intent) {
        return intent.getType() == CallbackType.SHOW_SUB_NEW;
    }

    @Override
    public void execute(CallbackUpdateIntent intent) throws TelegramApiException {
        showSubHandler.showNew(Integer.parseInt(intent.getArgs()), intent.getUserId());
    }

    @Override
    public Class<CallbackUpdateIntent> getIntentType() {
        return CallbackUpdateIntent.class;
    }
}
