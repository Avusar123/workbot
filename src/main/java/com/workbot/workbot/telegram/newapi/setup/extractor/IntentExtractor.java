package com.workbot.workbot.telegram.newapi.setup.extractor;

import com.workbot.workbot.data.model.dto.UserDto;
import com.workbot.workbot.telegram.newapi.setup.intent.CallbackUpdateIntent;
import com.workbot.workbot.telegram.newapi.setup.intent.PaginationUpdateIntent;
import com.workbot.workbot.telegram.newapi.setup.intent.TextMessageUpdateIntent;
import com.workbot.workbot.telegram.newapi.setup.intent.UpdateIntent;
import com.workbot.workbot.telegram.newapi.setup.intent.type.CallbackType;
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
    private Extractor<CallbackUpdateIntent> callbackUpdateIntentExtractor;

    @Override
    public UpdateIntent extract(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            return new TextMessageUpdateIntent(
                    messageExtractor.extract(update),
                    update.getMessage().getText(),
                    userExtractor.extract(update).getId());
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
