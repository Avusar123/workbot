package com.workbot.workbot.telegram.handle.handler;

import com.workbot.workbot.data.model.dto.util.TelegramSafeString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class AdminNotifyHandler {
    @Autowired
    private OkHttpTelegramClient telegramClient;

    public void handle(String message, long adminId) throws TelegramApiException {
        telegramClient.execute(
                SendMessage
                        .builder()
                        .chatId(adminId)
                        .text("""
                                *Оповещение администратора*
                                %s
                                """.formatted(TelegramSafeString.escapeMarkdownV2(message)))
                        .parseMode(ParseMode.MARKDOWNV2)
                        .build());
    }
}