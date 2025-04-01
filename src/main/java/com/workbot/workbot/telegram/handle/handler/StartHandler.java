package com.workbot.workbot.telegram.handle.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class StartHandler {
    @Autowired
    private OkHttpTelegramClient telegramClient;

    public void handle(long userId) throws TelegramApiException {
        var message = SendMessage
                .builder()
                .text("Добро пожаловать в бота по поиску вакансий! Для навигации используйте меню в нижней части чата")
                .chatId(userId)
                .replyMarkup(ReplyKeyboardMarkup
                        .builder()
                        .keyboardRow(
                                new KeyboardRow(
                                        new KeyboardButton("Подписки")
                                )
                        )
                        .keyboardRow(
                                new KeyboardRow(
                                        new KeyboardButton("Поиск")
                                )
                        ).build())
                .build();

        telegramClient.execute(message);
    }
}
