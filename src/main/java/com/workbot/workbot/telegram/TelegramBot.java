package com.workbot.workbot.telegram;

import com.workbot.workbot.logic.service.user.UserService;
import com.workbot.workbot.telegram.event.TelegramUpdateRecievedEvent;
import com.workbot.workbot.telegram.util.UserContextHolder;
import com.workbot.workbot.telegram.util.UserProvider;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class TelegramBot implements LongPollingSingleThreadUpdateConsumer {

    @Autowired
    private TelegramBotsLongPollingApplication longPollingApplication;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private UserContextHolder userContextHolder;

    @Autowired
    private UserProvider userIdProvider;

    @Value("${tg.token}")
    private String token;

    @PostConstruct
    protected void init() {
        try {
            longPollingApplication.registerBot(token, this);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void consume(Update update) {
        try {
             userContextHolder.save(userIdProvider.get(update));
            eventPublisher.publishEvent(new TelegramUpdateRecievedEvent(this, update));
        } finally {
            userContextHolder.flush();
        }

    }
}
