package com.workbot.workbot.telegram.setup.extractor;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface Extractor<Model> {
    default boolean has(Update update) {
        return true;
    }

    Model extract(Update update);
}
