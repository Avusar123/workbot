package com.workbot.workbot.telegram;

import com.workbot.workbot.telegram.process.TelegramProcessor;
import com.workbot.workbot.telegram.setup.context.UpdateContext;
import com.workbot.workbot.telegram.setup.context.UpdateContextHolder;
import com.workbot.workbot.telegram.setup.extractor.Extractor;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Autowired
    private TelegramBotsLongPollingApplication longPollingApplication;

    @Autowired
    private Extractor<UpdateContext> contextExtractor;

    @Autowired
    private TelegramProcessor telegramProcessor;

    @Autowired
    private UpdateContextHolder contextHolder;

    @Value("${tg.token}")
    private String token;

    private final Map<Long, ReentrantLock> lockMap = new HashMap<>();

    @PostConstruct
    protected void init() throws TelegramApiException {
        longPollingApplication.registerBot(token, this);
    }

    @Override
    public void consume(Update update) {
        var context = contextExtractor.extract(update);

        var lock = lockMap.computeIfAbsent(context.user().getId(), (k) -> new ReentrantLock());

        contextHolder.set(context);

        try {
            lock.lock();

            telegramProcessor.process(update);

        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        } finally {
            contextHolder.flush();

            lock.unlock();
        }

    }
}
