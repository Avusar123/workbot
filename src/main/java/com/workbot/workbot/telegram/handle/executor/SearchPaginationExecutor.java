package com.workbot.workbot.telegram.handle.executor;

import com.workbot.workbot.telegram.handle.handler.SearchHandler;
import com.workbot.workbot.telegram.setup.context.UpdateContextHolder;
import com.workbot.workbot.telegram.setup.context.data.SearchFilterCacheData;
import com.workbot.workbot.telegram.setup.intent.PaginationUpdateIntent;
import com.workbot.workbot.telegram.setup.intent.type.HandlerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class SearchPaginationExecutor implements HandlerExecutor<PaginationUpdateIntent> {
    @Autowired
    private SearchHandler searchHandler;

    @Autowired
    private UpdateContextHolder updateContextHolder;

    @Override
    public boolean supports(PaginationUpdateIntent intent) {
        return intent.getPaginationContext().getHandlerType() == HandlerType.SEARCH
                && updateContextHolder.contains()
                && updateContextHolder.get().cacheData() instanceof SearchFilterCacheData;
    }

    @Override
    public void execute(PaginationUpdateIntent intent) throws TelegramApiException {
        searchHandler.handlePage(
                ((SearchFilterCacheData)updateContextHolder.get().cacheData()).getFilterDto(),
                intent.getUserId(),
                intent.getMessageId(),
                intent.getTargetPage());
    }

    @Override
    public Class<PaginationUpdateIntent> getIntentType() {
        return PaginationUpdateIntent.class;
    }
}
