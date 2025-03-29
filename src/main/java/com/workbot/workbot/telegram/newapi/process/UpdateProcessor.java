package com.workbot.workbot.telegram.newapi.process;

import com.workbot.workbot.telegram.newapi.handle.HandlerEntrypoint;
import com.workbot.workbot.telegram.newapi.setup.extractor.Extractor;
import com.workbot.workbot.telegram.newapi.setup.intent.UpdateIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class UpdateProcessor {
    @Autowired
    private Extractor<UpdateIntent> updateIntentExtractor;

    @Autowired
    private HandlerEntrypoint handlerEntrypoint;

    public void process(Update update) throws TelegramApiException {
        var intent = updateIntentExtractor.extract(update);

        handlerEntrypoint.handle(intent);
    }
}
