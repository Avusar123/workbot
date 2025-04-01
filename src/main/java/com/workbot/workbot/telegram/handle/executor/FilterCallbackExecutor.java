package com.workbot.workbot.telegram.handle.executor;

import com.workbot.workbot.telegram.handle.handler.filter.FilterHandler;
import com.workbot.workbot.telegram.setup.context.UpdateContextHolder;
import com.workbot.workbot.telegram.setup.context.data.FilterCacheData;
import com.workbot.workbot.telegram.setup.intent.CallbackUpdateIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class FilterCallbackExecutor implements HandlerExecutor<CallbackUpdateIntent> {
    @Autowired
    private UpdateContextHolder updateContextHolder;

    @Autowired
    private FilterHandler filterHandler;

    @Override
    public boolean supports(CallbackUpdateIntent intent) {
        return updateContextHolder.get().hasData() && updateContextHolder.get().cacheData() instanceof FilterCacheData;
    }

    @Override
    public void execute(CallbackUpdateIntent intent) throws TelegramApiException {
        filterHandler.continueState(intent);
    }

    @Override
    public Class<CallbackUpdateIntent> getIntentType() {
        return CallbackUpdateIntent.class;
    }
}
