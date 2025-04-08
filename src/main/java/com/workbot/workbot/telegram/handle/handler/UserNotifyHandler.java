package com.workbot.workbot.telegram.handle.handler;

import com.workbot.workbot.data.model.dto.util.TelegramSafeString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class UserNotifyHandler {
    @Autowired
    private OkHttpTelegramClient telegramClient;

    public void send(long userId, String message) throws TelegramApiException {
        telegramClient.execute(SendMessage
                        .builder()
                        .chatId(userId)
                        .text(TelegramSafeString.escapeMarkdownV2(message))
                        .parseMode(ParseMode.MARKDOWNV2)
                        .build());
    }
}
