package com.workbot.workbot.telegram.handle.handler.util;

import com.workbot.workbot.data.model.dto.VacancyDto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public final class VacancyUtil {
    private VacancyUtil() {}

    public static String formatVacancy(VacancyDto vacancyDto) {
        return """
                *Направление:* %s
                *Компания:* %s
                *Название*: %s
                *Была добавлена* %s \\(Это %d дней назад\\)
                """.formatted(vacancyDto.getArea(),
                vacancyDto.getCompany().getDisplayName(),
                vacancyDto.getTitle(),
                vacancyDto.getAdded().format(DateTimeFormatter.ofPattern("dd:MM:yyyy")),
                ChronoUnit.DAYS.between(vacancyDto.getAdded(), LocalDateTime.now()));
    }

    public static List<InlineKeyboardRow> generateMarkupRows(VacancyDto vacancyDto) {
        return List.of(
                new InlineKeyboardRow(
                        InlineKeyboardButton
                                .builder()
                                .text("Перейти на страницу")
                                .url(vacancyDto.getLink())
                                .build()
                )
        );
    }
}
