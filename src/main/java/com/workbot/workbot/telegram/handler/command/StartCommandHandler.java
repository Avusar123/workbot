package com.workbot.workbot.telegram.handler.command;

import com.workbot.workbot.telegram.event.TelegramUpdateRecievedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class StartCommandHandler extends CommandHandler {
    @Override
    protected String getTriggerCommand() {
        return "start";
    }

    @Override
    protected void work(Update update) throws TelegramApiException {
        var message = SendMessage
                .builder()
                .text("Добро пожаловать в бота по поиску вакансий! Для навигации используйте меню в нижней части чата")
                .chatId(userContextHolder.getCurrent().getId())
                .replyMarkup(ReplyKeyboardMarkup
                        .builder()
                        .keyboardRow(
                                new KeyboardRow(
                                        new KeyboardButton("Подписки"),
                                        new KeyboardButton("Поиск")
                                )
                        ).build())
                .build();

        okHttpTelegramClient.execute(message);
    }
}
