package com.workbot.workbot.telegram.process;

import com.workbot.workbot.telegram.handle.HandlerEntrypoint;
import com.workbot.workbot.telegram.setup.context.UpdateContextHolder;
import com.workbot.workbot.telegram.setup.extractor.Extractor;
import com.workbot.workbot.telegram.setup.intent.PaginationUpdateIntent;
import com.workbot.workbot.telegram.setup.intent.UpdateIntent;
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

    @Autowired
    private UpdateContextHolder holder;

    public void process(Update update) throws TelegramApiException {
        var intent = updateIntentExtractor.extract(update);

        if (intent instanceof PaginationUpdateIntent pagIntent) {
            if (pagIntent.getTargetPage() == pagIntent.getPaginationContext().getCurrentPage()) {
                return;
            }
        }

        handlerEntrypoint.handle(intent);
    }
}
