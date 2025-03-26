package com.workbot.workbot.telegram.newapi.processor.extractor;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface Extractor<T> {
    T extract(Update update);

    boolean has(Update update);
}
