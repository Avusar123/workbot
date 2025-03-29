package com.workbot.workbot.telegram.newapi.handle;

import com.workbot.workbot.telegram.newapi.setup.intent.UpdateIntent;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface HandlerEntrypoint {
    void handle(UpdateIntent updateIntent) throws TelegramApiException;
}
