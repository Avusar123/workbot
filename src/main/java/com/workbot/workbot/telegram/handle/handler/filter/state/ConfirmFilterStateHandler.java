package com.workbot.workbot.telegram.handle.handler.filter.state;

import com.workbot.workbot.telegram.handle.handler.util.CancelUtil;
import com.workbot.workbot.telegram.handle.handler.util.FilterUtil;
import com.workbot.workbot.telegram.setup.context.data.FilterCacheData;
import com.workbot.workbot.telegram.setup.intent.MessageUpdateIntent;
import com.workbot.workbot.telegram.setup.intent.type.CallbackType;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
public class ConfirmFilterStateHandler extends FilterStateHandler {
    @Override
    public void handle(FilterCacheData filterCacheData, MessageUpdateIntent intent) throws TelegramApiException {}

    @Override
    public void init(FilterCacheData filterCacheData, MessageUpdateIntent intent) throws TelegramApiException {
        telegramClient.execute(
                EditMessageText
                        .builder()
                        .text("""
                                *Подтверждение фильтра*
                                %s
                                """.formatted(FilterUtil.formatFilter(filterCacheData.getFilterDto())))
                        .parseMode(ParseMode.MARKDOWNV2)
                        .replyMarkup(
                                new InlineKeyboardMarkup(
                                        List.of(
                                                new InlineKeyboardRow(
                                                        InlineKeyboardButton
                                                                .builder()
                                                                .text("✅ Подтвердить")
                                                                .callbackData(CallbackType.CONFIRM_FILTER.toString())
                                                                .build()
                                                ),
                                                CancelUtil.createCancelRow()
                                        )
                                )
                        )
                        .chatId(intent.getUserId())
                        .messageId(intent.getMessageId())
                        .build()
        );
    }
}
