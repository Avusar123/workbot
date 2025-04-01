package com.workbot.workbot.telegram.handle.handler.filter;

import com.workbot.workbot.data.model.dto.FilterDto;
import com.workbot.workbot.telegram.setup.context.data.FilterCacheData;
import com.workbot.workbot.telegram.setup.intent.MessageUpdateIntent;
import com.workbot.workbot.telegram.setup.intent.type.HandlerType;
import com.workbot.workbot.telegram.setup.redis.DataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class FilterHandler {
    @Autowired
    private StateSwitcher stateSwitcher;

    @Autowired
    private DataRepo dataRepo;

    public void create(MessageUpdateIntent intent, HandlerType handlerType) throws TelegramApiException {
        var filter = new FilterDto();

        var cache = new FilterCacheData();

        cache.setHandler(handlerType);

        cache.setFilterDto(filter);

        stateSwitcher.switchState(FilterState.AREA, cache, intent);

        dataRepo.save(intent.getUserId(), intent.getMessageId(), cache);
    }

    public void continueState(MessageUpdateIntent intent) throws TelegramApiException {
        var cache = dataRepo.get(intent.getUserId(), intent.getMessageId());

        if (!(cache instanceof FilterCacheData filterCache)) {
            throw new IllegalArgumentException("Cache must be FilterCacheData");
        }

        stateSwitcher.continueState(filterCache.getFilterState(), filterCache, intent);

        dataRepo.save(intent.getUserId(), intent.getMessageId(), filterCache);
    }
}
