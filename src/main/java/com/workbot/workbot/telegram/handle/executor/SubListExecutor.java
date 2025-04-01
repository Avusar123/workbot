package com.workbot.workbot.telegram.handle.executor;

import com.workbot.workbot.telegram.handle.handler.SubListHandler;
import com.workbot.workbot.telegram.setup.intent.TextMessageUpdateIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class SubListExecutor implements HandlerExecutor<TextMessageUpdateIntent> {
    @Autowired
    private SubListHandler subListHandler;

    @Override
    public boolean supports(TextMessageUpdateIntent intent) {
        return intent.getText().equalsIgnoreCase("Подписки");
    }

    @Override
    public void execute(TextMessageUpdateIntent intent) throws TelegramApiException {
        subListHandler.show(0, intent.getUserId());
    }

    @Override
    public Class<TextMessageUpdateIntent> getIntentType() {
        return TextMessageUpdateIntent.class;
    }
}
