package com.workbot.workbot.telegram.handle.handler.filter.state;

import com.workbot.workbot.data.model.Area;
import com.workbot.workbot.logic.update.section.util.SectionProvider;
import com.workbot.workbot.telegram.handle.handler.filter.FilterState;
import com.workbot.workbot.telegram.setup.context.data.FilterCacheData;
import com.workbot.workbot.telegram.setup.intent.CallbackUpdateIntent;
import com.workbot.workbot.telegram.setup.intent.MessageUpdateIntent;
import com.workbot.workbot.telegram.setup.intent.type.CallbackType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class AreaFilterStateHandler extends FilterStateHandler {
    @Autowired
    private SectionProvider provider;

    @Override
    public void init(FilterCacheData filterCacheData, MessageUpdateIntent intent) throws TelegramApiException {
        telegramClient.execute(EditMessageText
                .builder()
                .text("""
                                *Создание фильтра*
                                Выберите направление из списка
                                """)
                .parseMode(ParseMode.MARKDOWNV2)
                .replyMarkup(
                        new InlineKeyboardMarkup(
                                provider.getAllAreas().stream().map(
                                        area -> new InlineKeyboardRow(InlineKeyboardButton
                                                .builder()
                                                .text(area.getDisplayName())
                                                .callbackData(CallbackType.CHOOSE_AREA + " " + area)
                                                .build())
                                ).toList()
                        )
                )
                .messageId(intent.getMessageId())
                .chatId(intent.getUserId())
                .build());
    }

    @Override
    public void handle(FilterCacheData filterCacheData, MessageUpdateIntent intent) throws TelegramApiException {
        if (intent instanceof CallbackUpdateIntent callbackUpdateIntent) {
            if (callbackUpdateIntent.getType() == CallbackType.CHOOSE_AREA) {
                filterCacheData.getFilterDto().setArea(Area.valueOf(callbackUpdateIntent.getArgs()));

                stateSwitcher.switchState(FilterState.COMPANIES, filterCacheData, callbackUpdateIntent);
            }
        }
    }
}
