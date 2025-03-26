package com.workbot.workbot.telegram;

import com.workbot.workbot.telegram.newapi.context.UpdateContext;
import com.workbot.workbot.telegram.newapi.context.UpdateContextHolder;
import com.workbot.workbot.telegram.newapi.processor.extractor.Extractor;
import com.workbot.workbot.telegram.newapi.processor.processor.UpdateProcessor;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(TelegramBot.class);

    @Autowired
    private TelegramBotsLongPollingApplication longPollingApplication;

    @Autowired
    private Extractor<UpdateContext> contextExtractor;

    @Autowired
    private UpdateProcessor updateProcessor;

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

        var lock = lockMap.computeIfAbsent(context.getUser().getId(), (_) -> new ReentrantLock());

        contextHolder.save(context);

        try {
            lock.lock();

            updateProcessor.process();

        } finally {
            contextHolder.flush();

            lock.unlock();
        }

    }
}
