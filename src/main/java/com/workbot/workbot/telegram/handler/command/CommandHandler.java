package com.workbot.workbot.telegram.handler.command;

import com.workbot.workbot.telegram.util.UserContextHolder;
import com.workbot.workbot.telegram.event.TelegramUpdateRecievedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public abstract class CommandHandler {
    @Autowired
    protected UserContextHolder userContextHolder;

    @Autowired
    protected OkHttpTelegramClient okHttpTelegramClient;

    protected abstract void work(Update update) throws TelegramApiException;

    protected abstract String getTriggerCommand();

    protected String getPrefix() {
        return "/";
    }

    @EventListener
    public void handleTelegram(TelegramUpdateRecievedEvent telegramUpdateRecievedEvent) throws TelegramApiException {
        var update = telegramUpdateRecievedEvent.getUpdate();

        if (update.hasMessage() && update.getMessage().hasText() && update.getMessage().getText().toLowerCase().startsWith(getPrefix() + getTriggerCommand())) {
            work(update);
        }
    }
}
