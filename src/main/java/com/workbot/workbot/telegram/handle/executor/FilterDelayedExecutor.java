package com.workbot.workbot.telegram.handle.executor;

import com.workbot.workbot.telegram.handle.handler.filter.FilterHandler;
import com.workbot.workbot.telegram.setup.context.UpdateContextHolder;
import com.workbot.workbot.telegram.setup.context.data.DelegatedCacheData;
import com.workbot.workbot.telegram.setup.context.data.FilterCacheData;
import com.workbot.workbot.telegram.setup.intent.DelegatedMessageUpdateIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class FilterDelayedExecutor implements HandlerExecutor<DelegatedMessageUpdateIntent> {
    @Autowired
    private UpdateContextHolder updateContextHolder;

    @Autowired
    private FilterHandler filterHandler;

    @Override
    public boolean supports(DelegatedMessageUpdateIntent intent) {
        return updateContextHolder.get().cacheData() instanceof DelegatedCacheData delegatedCacheData
                && delegatedCacheData.getInner() instanceof FilterCacheData;
    }

    @Override
    public void execute(DelegatedMessageUpdateIntent intent) throws TelegramApiException {
        filterHandler.continueDelegated(intent);
    }

    @Override
    public Class<DelegatedMessageUpdateIntent> getIntentType() {
        return DelegatedMessageUpdateIntent.class;
    }
}
