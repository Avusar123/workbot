package com.workbot.workbot.telegram.setup.extractor;

import com.workbot.workbot.data.model.dto.UserDto;
import com.workbot.workbot.telegram.setup.context.UpdateContext;
import com.workbot.workbot.telegram.setup.context.data.CacheData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class ContextExtractor implements Extractor<UpdateContext> {
    @Autowired
    private Extractor<UserDto> userExtractor;

    @Autowired
    private Extractor<CacheData> cacheDataExtractor;

    @Override
    public UpdateContext extract(Update update) {
        var user = userExtractor.extract(update);

        CacheData data = null;

        if (cacheDataExtractor.has(update)) {
            data = cacheDataExtractor.extract(update);
        }

        return new UpdateContext(user, data, update);

    }
}
