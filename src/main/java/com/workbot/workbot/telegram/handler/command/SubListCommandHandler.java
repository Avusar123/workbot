package com.workbot.workbot.telegram.handler.command;

import com.workbot.workbot.logic.service.user.UserService;
import com.workbot.workbot.telegram.event.telegram.CallbackRecieved;
import com.workbot.workbot.telegram.event.telegram.CallbackType;
import com.workbot.workbot.telegram.event.telegram.TextMessageRecieved;
import com.workbot.workbot.telegram.pagination.PageSwitcher;
import com.workbot.workbot.telegram.pagination.PaginationBuilder;
import com.workbot.workbot.telegram.pagination.PaginationHelper;
import com.workbot.workbot.telegram.pagination.PaginationState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class SubListCommandHandler extends CommandHandler implements PageSwitcher {
    @Autowired
    private UserService userService;

    @Autowired
    private PaginationHelper paginationHelper;

    @Override
    protected void work(TextMessageRecieved event) throws TelegramApiException {
        var user = userContextHolder.getCurrent();

        var subs = userService.getSubs(userContextHolder.getId(), 5, 0);

        var buttons = subs.stream().map(
                sub -> InlineKeyboardButton
                        .builder()
                        .callbackData(CallbackType.SHOW_SUB.getSerializedText() + sub.getId())
                        .text(sub.getTitle())
                        .build()
        ).collect(Collectors.toCollection(() -> new ArrayList<InlineKeyboardButton>()));

        buttons.add(InlineKeyboardButton
                .builder()
                .text("Создать подписку")
                .callbackData(CallbackType.CREATE_SUB.getSerializedText())
                .build());

        var rows = buttons.stream().map(
                InlineKeyboardRow::new).toList();

        rows.add(paginationHelper
                .inlineRowBuilder()
                .withCurrent(0)
                .assignSwitcher(this)
                .);

        var message = SendMessage
                .builder()
                .chatId(user.getId())
                .text("Ваши подписки")
                .replyMarkup(InlineKeyboardMarkup
                        .builder()
                        .keyboard(

                        )
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

    @Override
    public InlineKeyboardMarkup updatePage(PaginationState paginationState, CallbackRecieved event) {
        return null;
    }
}
