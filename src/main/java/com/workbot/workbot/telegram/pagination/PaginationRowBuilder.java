package com.workbot.workbot.telegram.pagination;

import com.workbot.workbot.telegram.event.telegram.CallbackType;
import org.springframework.data.domain.Page;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class PaginationRowBuilder {
    private PaginationRowBuilder() {}

    public static InlineKeyboardRow from(Page<?> page) {
        List<InlineKeyboardButton> buttons = new ArrayList<>();

        if (page.hasPrevious())
            buttons.add(
                    InlineKeyboardButton
                            .builder()
                            .text("⏮ " + (page.getNumber()))
                            .callbackData(CallbackType.PAGINATION_CHANGE + " " + (page.getNumber() - 1))
                            .build()
            );

        if (page.hasNext())
            buttons.add(
                    InlineKeyboardButton
                            .builder()
                            .text((page.getNumber() + 2) + " ⏭")
                            .callbackData(CallbackType.PAGINATION_CHANGE + " " + (page.getNumber() + 1))
                            .build()
            );

        return new InlineKeyboardRow(buttons);
    }
}
