package com.workbot.workbot.telegram.setup.extractor;

import com.workbot.workbot.telegram.setup.intent.CallbackUpdateIntent;
import com.workbot.workbot.telegram.setup.intent.type.CallbackType;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class CallbackIntentExtractor implements Extractor<CallbackUpdateIntent> {
    @Override
    public boolean has(Update update) {
        return update.hasCallbackQuery();
    }

    @Override
    public CallbackUpdateIntent extract(Update update) {
        var data = update.getCallbackQuery().getData();

        var dataSplitted = data.split(" ", 2);

        var type = CallbackType.valueOf(dataSplitted[0]);

        String args = null;

        if (dataSplitted.length == 2) {
            args = dataSplitted[1];
        }

        return new CallbackUpdateIntent(
                update.getCallbackQuery().getMessage().getMessageId(),
                update.getCallbackQuery().getFrom().getId(),
                type,
                args,
                update.getCallbackQuery().getId()
        );
    }
}
