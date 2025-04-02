package com.workbot.workbot.telegram.handle.handler.util;

import com.workbot.workbot.telegram.setup.intent.type.CallbackType;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

public final class CancelUtil {
    private CancelUtil() {};

    public static InlineKeyboardRow createCancelRow() {
        return new InlineKeyboardRow(
                InlineKeyboardButton
                        .builder()
                        .text("\uD83D\uDEAB Отмена создания")
                        .callbackData(CallbackType.CANCEL_FILTER.toString())
                        .build()
        );
    }
}
