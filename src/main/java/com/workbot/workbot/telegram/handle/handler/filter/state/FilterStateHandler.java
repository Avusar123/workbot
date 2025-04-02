package com.workbot.workbot.telegram.handle.handler.filter.state;

import com.workbot.workbot.telegram.handle.handler.filter.StateSwitcher;
import com.workbot.workbot.telegram.setup.context.data.FilterCacheData;
import com.workbot.workbot.telegram.setup.intent.MessageUpdateIntent;
import com.workbot.workbot.telegram.setup.redis.DataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public abstract class FilterStateHandler {
    @Autowired
    protected OkHttpTelegramClient telegramClient;

    @Autowired
    protected StateSwitcher stateSwitcher;

    @Autowired
    protected DataRepo repo;

    public abstract void handle(FilterCacheData filterCacheData, MessageUpdateIntent intent) throws TelegramApiException;

    public abstract void init(FilterCacheData filterCacheData, MessageUpdateIntent intent) throws TelegramApiException;
}
