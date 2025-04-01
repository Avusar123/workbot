package com.workbot.workbot.telegram.handle.handler.filter.state;

import com.workbot.workbot.telegram.setup.context.data.FilterCacheData;
import com.workbot.workbot.telegram.setup.intent.MessageUpdateIntent;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class KeywordsFilterStateHandler extends FilterStateHandler {
    @Override
    public void handle(FilterCacheData filterCacheData, MessageUpdateIntent intent) throws TelegramApiException {

    }

    @Override
    public void init(FilterCacheData filterCacheData, MessageUpdateIntent intent) throws TelegramApiException {

    }
}
