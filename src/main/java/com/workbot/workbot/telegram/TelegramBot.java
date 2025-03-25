package com.workbot.workbot.telegram;

import com.workbot.workbot.data.model.dto.UserDto;
import com.workbot.workbot.telegram.cache.repo.PendingModelRepo;
import com.workbot.workbot.telegram.event.update.CallbackRecieved;
import com.workbot.workbot.telegram.event.update.CallbackType;
import com.workbot.workbot.telegram.event.update.TextMessageRecieved;
import com.workbot.workbot.telegram.holder.PendingContextHolder;
import com.workbot.workbot.telegram.holder.UserContextHolder;
import com.workbot.workbot.telegram.holder.UserProvider;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class TelegramBot implements LongPollingSingleThreadUpdateConsumer {

    private static final Logger log = LoggerFactory.getLogger(TelegramBot.class);

    @Autowired
    private TelegramBotsLongPollingApplication longPollingApplication;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private UserContextHolder userContextHolder;

    @Autowired
    private PendingContextHolder pendingContextHolder;

    @Autowired
    private PendingModelRepo pendingModelRepo;

    @Autowired
    private UserProvider userIdProvider;

    @Value("${tg.token}")
    private String token;

    private final Map<Long, ReentrantLock> lockMap = new HashMap<>();

    @PostConstruct
    protected void init() throws TelegramApiException {
        longPollingApplication.registerBot(token, this);
    }

    @Override
    public void consume(Update update) {
        var user = userIdProvider.get(update);

        var lock = lockMap.computeIfAbsent(user.getId(), (_) -> new ReentrantLock());

        try {
            lock.lock();

            setUserHolder(user);

            publishEvents(update);
        } finally {
            userContextHolder.flush();

            pendingContextHolder.flush();

            lock.unlock();
        }

    }

    private void setUserHolder(UserDto user) {
        userContextHolder.save(user);
    }

    private void publishEvents(Update update) {
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                eventPublisher.publishEvent(new TextMessageRecieved(this, update, update.getMessage().getText()));
            }

            if (update.hasCallbackQuery()) {
                var data = update.getCallbackQuery().getData();

                var splittedData = data.split(" ", 2);

                var type = CallbackType.valueOf(splittedData[0]);

                String args = null;

                if (splittedData.length > 1) {
                    args = splittedData[1];
                }

                processPending(update);

                eventPublisher.publishEvent(new CallbackRecieved(this, update, type, args));
            }
        } catch (IllegalArgumentException ex) {
            log.debug("Event was not recognized with IllegalArgumentException");
        }
    }

    private void processPending(Update update) {
        var userId = update.getCallbackQuery().getFrom().getId();

        var messageId = update.getCallbackQuery().getMessage().getMessageId();

        if (pendingModelRepo.contains(userId, messageId)) {
            pendingContextHolder.save(pendingModelRepo.get(userId, messageId));
        }
    }
}
