package com.workbot.workbot.telegram.handler.callback;

import com.workbot.workbot.telegram.cache.repo.PaginationRepo;
import com.workbot.workbot.telegram.event.update.CallbackRecieved;
import com.workbot.workbot.telegram.event.update.CallbackType;
import com.workbot.workbot.telegram.event.cache.PaginationCalledEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;

@Component
public class PaginationHandler {
    @Autowired
    private PaginationRepo repo;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private OkHttpTelegramClient okHttpTelegramClient;

    @EventListener()
    public void callbackListener(CallbackRecieved event) {
        if (event.getType() == CallbackType.PAGINATION_CHANGE && !(event instanceof PaginationCalledEvent)) {
            var messageId = event.getUpdate().getCallbackQuery().getMessage().getMessageId();

            var userId = event.getUpdate().getCallbackQuery().getFrom().getId();

            var model = repo.get(userId, messageId);

            var targetPage = Integer.parseInt(event.getPayload());

            if (model.getCurrentPage() == targetPage) {
                return;
            }

            var eventObject = new PaginationCalledEvent(
                    this,
                    event.getUpdate(),
                    model,
                    targetPage);

            publisher.publishEvent(eventObject);
        }
    }
}
