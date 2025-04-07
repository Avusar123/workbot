package com.workbot.workbot.telegram.process;

import com.workbot.workbot.telegram.handle.HandlerEntrypoint;
import com.workbot.workbot.telegram.setup.event.CustomIntentEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class ExternalProcessor {
    @Autowired
    private HandlerEntrypoint handlerEntrypoint;

    @EventListener
    public void processIntent(CustomIntentEvent event) throws TelegramApiException {
        handlerEntrypoint.handle(event.getIntent());
    }
}
