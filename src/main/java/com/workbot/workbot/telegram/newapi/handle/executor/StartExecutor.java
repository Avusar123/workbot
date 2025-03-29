package com.workbot.workbot.telegram.newapi.handle.executor;

import com.workbot.workbot.telegram.newapi.handle.handler.StartHandler;
import com.workbot.workbot.telegram.newapi.setup.intent.TextMessageUpdateIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class StartExecutor implements HandlerExecutor<TextMessageUpdateIntent> {
    @Autowired
    private StartHandler handler;

    @Override
    public boolean supports(TextMessageUpdateIntent intent) {
        return intent.getText().equals("/start");
    }

    @Override
    public void execute(TextMessageUpdateIntent intent) throws TelegramApiException {
        handler.handle(intent.getUserId());
    }

    @Override
    public Class<TextMessageUpdateIntent> getIntentType() {
        return TextMessageUpdateIntent.class;
    }
}
