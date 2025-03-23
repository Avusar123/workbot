package com.workbot.workbot.telegram.handler.callback;

import com.workbot.workbot.telegram.event.update.CallbackRecieved;
import com.workbot.workbot.telegram.event.update.CallbackType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;

@Component
public class CreateSubHandler {
    @Autowired
    private OkHttpTelegramClient okHttpTelegramClient;

    @EventListener
    public void callbackHandler(CallbackRecieved event) {
        if (event.getType() == CallbackType.CREATE_SUB) {

        }
    }
}
