package com.workbot.workbot.telegram.setup.context;

import com.workbot.workbot.data.model.dto.UserDto;
import com.workbot.workbot.telegram.setup.context.data.CacheData;
import org.telegram.telegrambots.meta.api.objects.Update;

public record UpdateContext(UserDto user, CacheData cacheData, Update update) {
    public boolean hasData() {
        return cacheData != null;
    }
}