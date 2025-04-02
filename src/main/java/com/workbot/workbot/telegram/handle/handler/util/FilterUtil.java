package com.workbot.workbot.telegram.handle.handler.util;

import com.workbot.workbot.data.model.dto.FilterDto;

import java.time.format.DateTimeFormatter;

public final class FilterUtil {
    private FilterUtil() {}

    public static String formatFilter(FilterDto filterDto) {
        return """
                *Направление:* %s
                *Выбрано компаний:* %s
                *Ключевые слова:* %s
                *Дата не раньше:* %s
                """.formatted(filterDto.getArea(),
                filterDto.getCompanies().size(),
                (!filterDto.getKeywords().isEmpty()) ? String.join(", ", filterDto.getKeywords()) : "Не заданы",
                filterDto.getDate().format(DateTimeFormatter.ofPattern("dd:MM:yyyy")));
    }
}
