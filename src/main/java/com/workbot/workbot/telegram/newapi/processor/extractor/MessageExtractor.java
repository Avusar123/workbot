package com.workbot.workbot.telegram.newapi.processor.extractor;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class MessageExtractor implements Extractor<Integer> {
    @Override
    public Integer extract(Update update) {
        int messageId;

        if (update.hasMessage()) {
            messageId = update.getMessage().getMessageId();
        } else if (update.hasCallbackQuery()) {
            messageId = update.getCallbackQuery().getMessage().getMessageId();
        } else {
            throw new UnsupportedOperationException("Update does not have message");
        }

        return messageId;
    }

    @Override
    public boolean has(Update update) {
        return true;
    }
}
