package com.workbot.workbot.telegram.newapi.handle.executor;

import com.workbot.workbot.telegram.newapi.setup.intent.UpdateIntent;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface HandlerExecutor<IntentType extends UpdateIntent> {
    boolean supports(IntentType intent);

    void execute(IntentType intent) throws TelegramApiException;

    Class<IntentType> getIntentType();
}
