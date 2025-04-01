package com.workbot.workbot.telegram.handle.executor;

import com.workbot.workbot.telegram.handle.handler.filter.FilterHandler;
import com.workbot.workbot.telegram.setup.context.UpdateContextHolder;
import com.workbot.workbot.telegram.setup.context.data.FilterCacheData;
import com.workbot.workbot.telegram.setup.intent.PaginationUpdateIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class FilterPaginationExecutor implements HandlerExecutor<PaginationUpdateIntent> {
    @Autowired
    private FilterHandler filterHandler;

    @Autowired
    private UpdateContextHolder updateContextHolder;

    @Override
    public boolean supports(PaginationUpdateIntent intent) {
        return updateContextHolder.get().hasData() && updateContextHolder.get().cacheData() instanceof FilterCacheData;
    }

    @Override
    public void execute(PaginationUpdateIntent intent) throws TelegramApiException {
        filterHandler.continueState(intent);
    }

    @Override
    public Class<PaginationUpdateIntent> getIntentType() {
        return PaginationUpdateIntent.class;
    }
}
