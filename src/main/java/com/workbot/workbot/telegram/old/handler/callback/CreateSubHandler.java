package com.workbot.workbot.telegram.old.handler.callback;

import com.workbot.workbot.telegram.old.event.cache.FilterRequiredEvent;
import com.workbot.workbot.telegram.old.event.update.CallbackRecieved;
import com.workbot.workbot.telegram.old.event.update.CallbackType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;

@Component
public class CreateSubHandler {
    @Autowired
    private OkHttpTelegramClient okHttpTelegramClient;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @EventListener
    public void callbackHandler(CallbackRecieved event) {
        if (event.getType() == CallbackType.CREATE_SUB) {
            var newEvent = new FilterRequiredEvent(
                    this,
                    event.getUpdate(),
                    "create-sub",
                    event.getUpdate().getCallbackQuery().getMessage().getMessageId()
            );

            eventPublisher.publishEvent(newEvent);
        }
    }
}
