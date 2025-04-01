package com.workbot.workbot.telegram.handle.handler;

import com.workbot.workbot.telegram.handle.handler.filter.FilterHandler;
import com.workbot.workbot.telegram.setup.intent.MessageUpdateIntent;
import com.workbot.workbot.telegram.setup.intent.type.HandlerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class CreateSubHandler {
    @Autowired
    private FilterHandler filterHandler;

    public void handle(MessageUpdateIntent intent) throws TelegramApiException {
        filterHandler.create(intent, HandlerType.SUBLIST);
    }
}
