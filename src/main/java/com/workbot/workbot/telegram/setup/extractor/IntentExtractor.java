package com.workbot.workbot.telegram.setup.extractor;

import com.workbot.workbot.data.model.dto.UserDto;
import com.workbot.workbot.telegram.setup.context.UpdateContextHolder;
import com.workbot.workbot.telegram.setup.context.data.DelegatedCacheData;
import com.workbot.workbot.telegram.setup.intent.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class IntentExtractor implements Extractor<UpdateIntent> {
    @Autowired
    private MessageExtractor messageExtractor;

    @Autowired
    private Extractor<UserDto> userExtractor;

    @Autowired
    private Extractor<PaginationUpdateIntent> paginationUpdateIntentExtractor;

    @Autowired
    private UpdateContextHolder updateContextHolder;

    @Autowired
    private Extractor<CallbackUpdateIntent> callbackUpdateIntentExtractor;

    @Override
    public UpdateIntent extract(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            var intent = new TextMessageUpdateIntent(
                    messageExtractor.extract(update),
                    update.getMessage().getText(),
                    userExtractor.extract(update).getId());

            if (updateContextHolder.get().cacheData() instanceof DelegatedCacheData cacheData) {
                return new DelegatedMessageUpdateIntent(cacheData.getSourceMessageId(), intent);
            }

            return intent;
        }

        if (update.hasCallbackQuery()) {
            if (paginationUpdateIntentExtractor.has(update)) {
                return paginationUpdateIntentExtractor.extract(update);
            }


            if (callbackUpdateIntentExtractor.has(update)) {
                return callbackUpdateIntentExtractor.extract(update);
            }
        }

        throw new UnsupportedOperationException("Cannot extract intent from that update");
    }
}
