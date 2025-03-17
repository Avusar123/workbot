package com.workbot.workbot.telegram.handler.command;

import com.workbot.workbot.logic.service.user.UserService;
import com.workbot.workbot.telegram.handler.CallbackType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class SubListCommandHandler extends CommandHandler {
    @Autowired
    private UserService userService;

    @Override
    protected void work(Update update) throws TelegramApiException {
        var user = userContextHolder.getCurrent();

        var subs = userService.getSubs(userContextHolder.getId(), 5, 0);

        var buttons = subs.stream().map(
                sub -> InlineKeyboardButton
                        .builder()
                        .callbackData(CallbackType.SHOW_SUB.getSerialized() + sub.getId())
                        .text(sub.getTitle())
                        .build()
        ).collect(Collectors.toCollection(() -> new ArrayList<InlineKeyboardButton>()));

        buttons.add(InlineKeyboardButton
                .builder()
                .text("Создать подписку")
                .callbackData(CallbackType.NEW_SUB.getSerialized())
                .build());

        var message = SendMessage
                .builder()
                .chatId(user.getId())
                .text("Ваши подписки")
                .replyMarkup(InlineKeyboardMarkup
                        .builder()
                        .keyboardRow(new InlineKeyboardRow(buttons))
                        .build())
                .build();

        okHttpTelegramClient.execute(message);
    }

    @Override
    protected String getPrefix() {
        return "";
    }

    @Override
    protected String getTriggerCommand() {
        return "подписки";
    }
}
