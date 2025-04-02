package com.workbot.workbot.telegram.handle.executor;

import com.workbot.workbot.telegram.handle.handler.SearchHandler;
import com.workbot.workbot.telegram.setup.context.UpdateContextHolder;
import com.workbot.workbot.telegram.setup.context.data.FilterCacheData;
import com.workbot.workbot.telegram.setup.intent.CallbackUpdateIntent;
import com.workbot.workbot.telegram.setup.intent.type.CallbackType;
import com.workbot.workbot.telegram.setup.intent.type.HandlerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class SearchConfirmedFilterExecutor implements HandlerExecutor<CallbackUpdateIntent> {
    @Autowired
    private UpdateContextHolder updateContextHolder;

    @Autowired
    private SearchHandler searchHandler;

    @Override
    public boolean supports(CallbackUpdateIntent intent) {
        return intent.getType() == CallbackType.CONFIRM_FILTER
                && updateContextHolder.get().cacheData() instanceof FilterCacheData filterCacheData
                && filterCacheData.getHandler() == HandlerType.SEARCH;
    }

    @Override
    public void execute(CallbackUpdateIntent intent) throws TelegramApiException {
        searchHandler.handlePage(((FilterCacheData)updateContextHolder.get().cacheData()).getFilterDto(),
                intent.getUserId(),
                intent.getMessageId(),
                0);
    }

    @Override
    public Class<CallbackUpdateIntent> getIntentType() {
        return CallbackUpdateIntent.class;
    }
}
