package com.workbot.workbot.telegram.handler.callback;

import com.workbot.workbot.telegram.event.telegram.CallbackRecieved;
import com.workbot.workbot.telegram.event.telegram.CallbackType;
import com.workbot.workbot.telegram.pagination.PaginationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class PaginationHandler {

    @Autowired
    private PaginationRepo paginationRepo;

    @Autowired
    private OkHttpTelegramClient okHttpTelegramClient;

    @EventListener
    public void callbackListener(CallbackRecieved event) throws TelegramApiException {
        if (event.getType() == CallbackType.NEXT_PAGE || event.getType() == CallbackType.PREV_PAGE) {
            var userId = event.getUpdate().getCallbackQuery().getFrom().getId();

            var messageId = event.getUpdate().getCallbackQuery().getMessage().getMessageId();

            var state = paginationRepo.get(userId, messageId.longValue());

            switch (event.getType()) {
                case NEXT_PAGE -> state.setCurrent(state.current() + 1);
                case PREV_PAGE -> state.setCurrent(state.current() - 1);
            }

            var markup = state.callbackObject().updatePage(state, event);

            okHttpTelegramClient.execute(EditMessageReplyMarkup
                    .builder()
                    .messageId(messageId)
                    .chatId(userId)
                    .replyMarkup(markup)
                    .build());
        }
    }
}
