package com.workbot.workbot.telegram.handle.executor;

import com.workbot.workbot.telegram.handle.handler.CreateSubHandler;
import com.workbot.workbot.telegram.setup.context.UpdateContextHolder;
import com.workbot.workbot.telegram.setup.context.data.DelegatedCacheData;
import com.workbot.workbot.telegram.setup.context.data.SubCacheData;
import com.workbot.workbot.telegram.setup.intent.DelegatedMessageUpdateIntent;
import com.workbot.workbot.telegram.setup.intent.TextMessageUpdateIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class SubTitleDelayedExecutor implements HandlerExecutor<DelegatedMessageUpdateIntent> {
    @Autowired
    private UpdateContextHolder updateContextHolder;

    @Autowired
    private CreateSubHandler createSubHandler;

    @Override
    public boolean supports(DelegatedMessageUpdateIntent intent) {
        return updateContextHolder.get().hasData()
                && updateContextHolder.get().cacheData() instanceof DelegatedCacheData delegatedCacheData
                && delegatedCacheData.getInner() instanceof SubCacheData
                && intent.getInner() instanceof TextMessageUpdateIntent;
    }

    @Override
    public void execute(DelegatedMessageUpdateIntent intent) throws TelegramApiException {
        createSubHandler.processTitle(
                ((TextMessageUpdateIntent) intent.getInner()).getText(),
                (SubCacheData) ((DelegatedCacheData) updateContextHolder.get().cacheData()).getInner(),
                intent.getUserId(),
                intent.getSourceMessageId());
    }

    @Override
    public Class<DelegatedMessageUpdateIntent> getIntentType() {
        return DelegatedMessageUpdateIntent.class;
    }
}
