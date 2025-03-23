package com.workbot.workbot.telegram.event.cache;

import com.workbot.workbot.telegram.event.update.TelegramUpdateRecievedEvent;
import org.telegram.telegrambots.meta.api.objects.Update;

public class FilterRequiredEvent extends TelegramUpdateRecievedEvent {

    public FilterRequiredEvent(Object source, Update update) {
        super(source, update);
    }
}
