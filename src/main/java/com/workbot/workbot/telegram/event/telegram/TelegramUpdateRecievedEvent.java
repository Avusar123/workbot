package com.workbot.workbot.telegram.event.telegram;

import org.springframework.context.ApplicationEvent;

import org.telegram.telegrambots.meta.api.objects.Update;

public class TelegramUpdateRecievedEvent extends ApplicationEvent {

    Update update;

    public TelegramUpdateRecievedEvent(Object source, Update update) {
        super(source);
        this.update = update;
    }

    public Update getUpdate() {
        return update;
    }
}
