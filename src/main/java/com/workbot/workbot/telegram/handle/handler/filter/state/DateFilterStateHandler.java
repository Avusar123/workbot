package com.workbot.workbot.telegram.handle.handler.filter.state;

import com.workbot.workbot.telegram.handle.handler.filter.FilterState;
import com.workbot.workbot.telegram.setup.context.data.FilterCacheData;
import com.workbot.workbot.telegram.setup.intent.CallbackUpdateIntent;
import com.workbot.workbot.telegram.setup.intent.MessageUpdateIntent;
import com.workbot.workbot.telegram.setup.intent.type.CallbackType;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DateFilterStateHandler extends FilterStateHandler {
    @Override
    public void handle(FilterCacheData filterCacheData, MessageUpdateIntent intent) throws TelegramApiException {
        if (intent instanceof CallbackUpdateIntent callbackUpdateIntent && callbackUpdateIntent.getType() == CallbackType.CHOOSE_DATE) {
            var offset = Integer.parseInt(callbackUpdateIntent.getArgs());

            filterCacheData.getFilterDto().setDate(LocalDateTime.now().minusHours(offset));

            stateSwitcher.switchState(FilterState.CONFIRM, filterCacheData, intent);
        }
    }

    @Override
    public void init(FilterCacheData filterCacheData, MessageUpdateIntent intent) throws TelegramApiException {
        telegramClient.execute(
                EditMessageText
                        .builder()
                        .text("""
                                *Создание фильтра*
                                Выберите в рамках какого временного периода вы хотите получать вакансии
                                """)
                        .replyMarkup(
                                new InlineKeyboardMarkup(
                                        List.of(
                                                new InlineKeyboardRow(
                                                        InlineKeyboardButton
                                                                .builder()
                                                                .text("Сегодня (24 часа)")
                                                                .callbackData(CallbackType.CHOOSE_DATE + " " + 24)
                                                                .build()
                                                ),
                                                new InlineKeyboardRow(
                                                        InlineKeyboardButton
                                                                .builder()
                                                                .text("Неделя (7 дней от текущей)")
                                                                .callbackData(CallbackType.CHOOSE_DATE + " " + 7 * 24)
                                                                .build()
                                                ),
                                                new InlineKeyboardRow(
                                                        InlineKeyboardButton
                                                                .builder()
                                                                .text("Месяц (30 дней от текущей)")
                                                                .callbackData(CallbackType.CHOOSE_DATE + " " + 30 * 24)
                                                                .build()
                                                ),
                                                new InlineKeyboardRow(
                                                        InlineKeyboardButton
                                                                .builder()
                                                                .text("3 месяца (90 дней от текущей)")
                                                                .callbackData(CallbackType.CHOOSE_DATE + " " + 90 * 24)
                                                                .build()
                                                ),
                                                new InlineKeyboardRow(
                                                        InlineKeyboardButton
                                                                .builder()
                                                                .text("Полгода (180 дней от текущей")
                                                                .callbackData(CallbackType.CHOOSE_DATE + " " + 180 * 24)
                                                                .build()
                                                ),
                                                new InlineKeyboardRow(
                                                        InlineKeyboardButton
                                                                .builder()
                                                                .text("Год (360 дней от текущей")
                                                                .callbackData(CallbackType.CHOOSE_DATE + " " + 360 * 24)
                                                                .build()
                                                )
                                        )
                                )
                        )
                        .chatId(intent.getUserId())
                        .messageId(intent.getMessageId())
                        .parseMode(ParseMode.MARKDOWNV2)
                        .build()
        );
    }
}
