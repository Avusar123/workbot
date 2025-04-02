package com.workbot.workbot.telegram.setup.extractor;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class MessageExtractor implements Extractor<Integer> {
    @Override
    public Integer extract(Update update) {
        if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getMessage().getMessageId();
        }

        if (update.hasMessage()) {
            return update.getMessage().getMessageId();
        }

        throw new UnsupportedOperationException("Cannot extract message from that update");
    }
}
