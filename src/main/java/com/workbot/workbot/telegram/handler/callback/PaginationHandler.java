package com.workbot.workbot.telegram.handler.callback;

import com.workbot.workbot.telegram.event.telegram.CallbackRecieved;
import com.workbot.workbot.telegram.event.telegram.CallbackType;
import com.workbot.workbot.telegram.event.telegram.PaginationCalledEvent;
import com.workbot.workbot.telegram.pagination.PaginationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
public class PaginationHandler {
    @Autowired
    private PaginationRepo repo;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private OkHttpTelegramClient okHttpTelegramClient;

    @EventListener()
    public void callbackListener(CallbackRecieved event) throws TelegramApiException {
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
