package com.workbot.workbot.telegram.handle.executor;

import com.workbot.workbot.telegram.handle.handler.SearchHandler;
import com.workbot.workbot.telegram.setup.intent.CallbackUpdateIntent;
import com.workbot.workbot.telegram.setup.intent.type.CallbackType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class InitSearchExecutor implements HandlerExecutor<CallbackUpdateIntent> {
    @Autowired
    private SearchHandler searchHandler;

    @Override
    public boolean supports(CallbackUpdateIntent intent) {
        return intent.getType() == CallbackType.INIT_SEARCH;
    }

    @Override
    public void execute(CallbackUpdateIntent intent) throws TelegramApiException {
        searchHandler.process(intent);
    }

    @Override
    public Class<CallbackUpdateIntent> getIntentType() {
        return CallbackUpdateIntent.class;
    }
}
