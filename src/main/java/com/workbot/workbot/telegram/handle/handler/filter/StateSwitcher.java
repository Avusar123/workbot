package com.workbot.workbot.telegram.handle.handler.filter;

import com.workbot.workbot.telegram.setup.context.data.FilterCacheData;
import com.workbot.workbot.telegram.setup.intent.MessageUpdateIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class StateSwitcher {
    @Autowired
    private StateResolver stateResolver;

    public void switchState(FilterState state, FilterCacheData cacheData, MessageUpdateIntent intent) throws TelegramApiException {
        var resolver = stateResolver.resolve(state);

        cacheData.setFilterState(state);

        resolver.init(cacheData, intent);
    }

    public void continueState(FilterState state, FilterCacheData cacheData, MessageUpdateIntent intent) throws TelegramApiException {
        var resolver = stateResolver.resolve(state);

        resolver.handle(cacheData, intent);
    }
}
