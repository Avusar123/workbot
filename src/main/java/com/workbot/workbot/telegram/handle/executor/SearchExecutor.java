package com.workbot.workbot.telegram.handle.executor;

import com.workbot.workbot.telegram.handle.handler.SearchHandler;
import com.workbot.workbot.telegram.setup.intent.TextMessageUpdateIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class SearchExecutor implements HandlerExecutor<TextMessageUpdateIntent> {
    @Autowired
    private SearchHandler handler;

    @Override
    public boolean supports(TextMessageUpdateIntent intent) {
        return intent.getText().equalsIgnoreCase("Поиск");
    }

    @Override
    public void execute(TextMessageUpdateIntent intent) throws TelegramApiException {
        handler.init(intent.getUserId());
    }

    @Override
    public Class<TextMessageUpdateIntent> getIntentType() {
        return TextMessageUpdateIntent.class;
    }
}
