package com.workbot.workbot.telegram.handle.executor;

import com.workbot.workbot.telegram.handle.handler.CreateSubHandler;
import com.workbot.workbot.telegram.setup.context.UpdateContextHolder;
import com.workbot.workbot.telegram.setup.context.data.FilterCacheData;
import com.workbot.workbot.telegram.setup.intent.CallbackUpdateIntent;
import com.workbot.workbot.telegram.setup.intent.type.CallbackType;
import com.workbot.workbot.telegram.setup.intent.type.HandlerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class CreateSubConfirmedFilterExecutor implements HandlerExecutor<CallbackUpdateIntent> {
    @Autowired
    private UpdateContextHolder updateContextHolder;

    @Autowired
    private CreateSubHandler createSubHandler;

    @Override
    public boolean supports(CallbackUpdateIntent intent) {
        return intent.getType() == CallbackType.CONFIRM_FILTER
                && updateContextHolder.get().cacheData() instanceof FilterCacheData filterCacheData
                && filterCacheData.getHandler() == HandlerType.CREATE_SUB;
    }

    @Override
    public void execute(CallbackUpdateIntent intent) throws TelegramApiException {
        createSubHandler.afterFilter(intent.getUserId(), intent.getMessageId(), ((FilterCacheData)updateContextHolder.get().cacheData()).getFilterDto());
    }

    @Override
    public Class<CallbackUpdateIntent> getIntentType() {
        return CallbackUpdateIntent.class;
    }
}
