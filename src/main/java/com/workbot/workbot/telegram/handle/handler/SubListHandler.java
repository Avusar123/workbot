package com.workbot.workbot.telegram.handle.handler;

import com.workbot.workbot.data.model.dto.SubscriptionDto;
import com.workbot.workbot.logic.service.user.UserService;
import com.workbot.workbot.telegram.handle.handler.util.PaginationUtil;
import com.workbot.workbot.telegram.setup.intent.type.CallbackType;
import com.workbot.workbot.telegram.setup.intent.type.HandlerType;
import com.workbot.workbot.telegram.setup.redis.PaginationContext;
import com.workbot.workbot.telegram.setup.redis.PaginationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SubListHandler {
    @Autowired
    private UserService userService;

    @Autowired
    private OkHttpTelegramClient telegramClient;

    @Autowired
    private PaginationRepo paginationRepo;

    public void show(int targetPage, long userId) throws TelegramApiException {
        var subs = userService.getSubs(userId, PaginationUtil.PAGE_SIZE, targetPage);

        var markup = generateMarkup(subs);

        SendMessage message = SendMessage
                .builder()
                .text("""
                        *Ваши подписки*
                        Страница %d из %d
                        """.formatted(subs.getNumber() + 1, subs.getTotalPages()))
                .parseMode(ParseMode.MARKDOWNV2)
                .replyMarkup(
                        markup
                )
                .chatId(userId)
                .build();

        var result = telegramClient.execute(message);

        var pagContext = new PaginationContext(targetPage, HandlerType.SUBLIST);

        paginationRepo.save(userId, result.getMessageId(), pagContext);
    }

    public void edit(int targetPage, long userId, int messageId) throws TelegramApiException {
        var subs = userService.getSubs(userId, PaginationUtil.PAGE_SIZE, targetPage);

        var markup = generateMarkup(subs);

        EditMessageText editMessage = EditMessageText
                .builder()
                .replyMarkup(markup)
                .text("""
                        *Ваши подписки*
                        Страница %d из %d
                        """.formatted(subs.getNumber() + 1, subs.getTotalPages()))
                .chatId(userId)
                .parseMode(ParseMode.MARKDOWNV2)
                .messageId(messageId)
                .build();

        telegramClient.execute(editMessage);

        var pagContext = new PaginationContext(targetPage, HandlerType.SUBLIST);

        paginationRepo.save(userId, messageId, pagContext);
    }

    private InlineKeyboardMarkup generateMarkup(Page<SubscriptionDto> subs) {
        List<InlineKeyboardRow> rows = subs.map(
                sub -> new InlineKeyboardRow(
                        InlineKeyboardButton
                                .builder()
                                .text(sub.getTitle())
                                .callbackData(CallbackType.SHOW_SUB + " " + sub.getId())
                                .build()
                )
        ).stream().collect(Collectors.toCollection(ArrayList::new));

        if (subs.getTotalPages() >= 1)
            rows.add(
                    PaginationUtil.paginationRow(subs)
            );

        rows.add(
                new InlineKeyboardRow(
                        InlineKeyboardButton
                                .builder()
                                .text("Создать подписку")
                                .callbackData(CallbackType.CREATE_SUB.toString())
                                .build()
                )
        );

        return new InlineKeyboardMarkup(rows);
    }
}
