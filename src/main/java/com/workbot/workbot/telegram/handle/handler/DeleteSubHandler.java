package com.workbot.workbot.telegram.handle.handler;

import com.workbot.workbot.logic.service.sub.SubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class DeleteSubHandler {
    @Autowired
    private SubService subService;

    @Autowired
    private OkHttpTelegramClient telegramClient;

    public void delete(int subId, long userId, int messageId) throws TelegramApiException {
        subService.delete(subId);

        telegramClient.execute(EditMessageText
                        .builder()
                        .chatId(userId)
                        .messageId(messageId)
                        .text("Подписка успешно удалена!")
                        .build());
    }
}
