package com.workbot.workbot.telegram.handler.command;

import com.workbot.workbot.telegram.event.update.TextMessageRecieved;
import com.workbot.workbot.telegram.util.UserContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public abstract class CommandHandler {
    @Autowired
    protected UserContextHolder userContextHolder;

    @Autowired
    protected OkHttpTelegramClient okHttpTelegramClient;

    protected abstract void work(TextMessageRecieved event) throws TelegramApiException;

    protected abstract String getTriggerCommand();

    protected String getPrefix() {
        return "/";
    }

    @EventListener
    public void handleTelegram(TextMessageRecieved event) throws TelegramApiException {
        if (event.getText().toLowerCase().startsWith(getPrefix() + getTriggerCommand())) {
            work(event);
        }
    }
}
