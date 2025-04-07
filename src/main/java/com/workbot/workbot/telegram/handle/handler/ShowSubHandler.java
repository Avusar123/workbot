package com.workbot.workbot.telegram.handle.handler;

import com.workbot.workbot.data.model.dto.SubscriptionDto;
import com.workbot.workbot.logic.service.sub.SubService;
import com.workbot.workbot.telegram.handle.handler.util.FilterUtil;
import com.workbot.workbot.telegram.setup.intent.type.CallbackType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
public class ShowSubHandler {
    @Autowired
    private OkHttpTelegramClient telegramClient;

    @Autowired
    private SubService subService;

    public void show(int subId, long userId, int messageId) throws TelegramApiException {
        var sub = subService.get(subId, userId);

        var text = generateText(sub);

        telegramClient.execute(
                EditMessageText
                        .builder()
                        .text(text)
                        .chatId(userId)
                        .messageId(messageId)
                        .parseMode(ParseMode.MARKDOWNV2)
                        .replyMarkup(
                                generateMarkup(subId)
                        )
                        .build()
        );
    }

    public void showNew(int subId, long userId) throws TelegramApiException {
        var sub = subService.get(subId, userId);

        var text = generateText(sub);

        telegramClient.execute(
                SendMessage
                        .builder()
                        .text(text)
                        .chatId(userId)
                        .parseMode(ParseMode.MARKDOWNV2)
                        .replyMarkup(
                                generateMarkup(subId)
                        )
                        .build()
        );
    }

    private InlineKeyboardMarkup generateMarkup(int subId) {
        return new InlineKeyboardMarkup(
                List.of(
                        new InlineKeyboardRow(
                                InlineKeyboardButton
                                        .builder()
                                        .text("Удалить подписку")
                                        .callbackData(CallbackType.DELETE_SUB + " " + subId)
                                        .build()
                        )
                )
        );
    }

    private String generateText(SubscriptionDto subscriptionDto) {
        return """
                *Название:* %s
                _Фильтр_
                %s
                """.formatted(subscriptionDto.getTitle(), FilterUtil.formatFilter(subscriptionDto.getFilter()));
    }
}
