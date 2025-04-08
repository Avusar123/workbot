package com.workbot.workbot.telegram.handle.executor;

import com.workbot.workbot.telegram.handle.handler.UserNotifyHandler;
import com.workbot.workbot.telegram.setup.intent.UsersNotifyIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class UsersNotifyExecutor implements HandlerExecutor<UsersNotifyIntent> {
    @Autowired
    private UserNotifyHandler userNotifyHandler;

    @Override
    public boolean supports(UsersNotifyIntent intent) {
        return true;
    }

    @Override
    public void execute(UsersNotifyIntent intent) throws TelegramApiException {
        for (var user : intent.getUsers()) {
            userNotifyHandler.send(user, intent.getMessage());
        }
    }

    @Override
    public Class<UsersNotifyIntent> getIntentType() {
        return UsersNotifyIntent.class;
    }
}
