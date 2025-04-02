package com.workbot.workbot.telegram.handle.handler;

import com.workbot.workbot.telegram.setup.redis.ChatDelegatedMessagesRepo;
import com.workbot.workbot.telegram.setup.redis.DataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

@Component
public class CancelOperationHandler {
    @Autowired
    private ChatDelegatedMessagesRepo chatDelegatedMessagesRepo;

    @Autowired
    private DataRepo dataRepo;

    @Autowired
    private OkHttpTelegramClient telegramClient;

    public void cancel(long userId, int messageId) throws TelegramApiException {
        chatDelegatedMessagesRepo.flush(userId);

        dataRepo.flush(userId, messageId);

        EditMessageText editMessageText = EditMessageText
                .builder()
                .text("Действие успешно отменено!")
                .messageId(messageId)
                .chatId(userId)
                .build();


        try {
            telegramClient.execute(editMessageText);
        }  catch (TelegramApiException ex) {
            if (!(ex instanceof TelegramApiRequestException requestException && requestException.getErrorCode() == 400)) {
                throw ex;
            }
        }

    }
}
