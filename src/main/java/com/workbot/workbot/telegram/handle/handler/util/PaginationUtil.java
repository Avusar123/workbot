package com.workbot.workbot.telegram.handle.handler.util;

import com.workbot.workbot.telegram.setup.intent.type.CallbackType;
import org.springframework.data.domain.Page;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.ArrayList;
import java.util.List;

public final class PaginationUtil {
    private PaginationUtil() {}

    public static final int PAGE_SIZE = 5;

    public static InlineKeyboardRow paginationRow(Page<?> page) {
        List<InlineKeyboardButton> buttons = new ArrayList<>();

        if (page.hasPrevious()) {
            buttons.add(
                    InlineKeyboardButton.
                            builder()
                            .text("⏪ " + page.getNumber())
                            .callbackData(CallbackType.PAGINATION + " " + (page.getNumber() - 1))
                            .build()
            );
        }

        if (page.hasNext()) {
            buttons.add(
                    InlineKeyboardButton
                            .builder()
                            .text((page.getNumber() + 2) + " ⏩")
                            .callbackData(CallbackType.PAGINATION + " " + (page.getNumber() + 1))
                            .build()
            );
        }

        return new InlineKeyboardRow(buttons);
    }
}
