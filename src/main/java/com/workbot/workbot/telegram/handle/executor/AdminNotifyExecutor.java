package com.workbot.workbot.telegram.handle.executor;

import com.workbot.workbot.telegram.handle.handler.AdminNotifyHandler;
import com.workbot.workbot.telegram.setup.intent.AdminNotifyIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class AdminNotifyExecutor implements HandlerExecutor<AdminNotifyIntent> {
    @Autowired
    private AdminNotifyHandler adminNotifyHandler;

    @Value("${tg.admin:1}")
    private long adminId;

    @Override
    public boolean supports(AdminNotifyIntent intent) {
        return true;
    }

    @Override
    public void execute(AdminNotifyIntent intent) throws TelegramApiException {
        adminNotifyHandler.handle(intent.getMessage(), adminId);
    }

    @Override
    public Class<AdminNotifyIntent> getIntentType() {
        return AdminNotifyIntent.class;
    }
}
