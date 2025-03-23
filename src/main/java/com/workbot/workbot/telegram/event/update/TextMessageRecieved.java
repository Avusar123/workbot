package com.workbot.workbot.telegram.event.update;

import org.telegram.telegrambots.meta.api.objects.Update;

public class TextMessageRecieved extends TelegramUpdateRecievedEvent {

    String text;

    public TextMessageRecieved(Object source, Update update, String text) {
        super(source, update);
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
