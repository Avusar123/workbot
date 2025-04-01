package com.workbot.workbot.telegram.handle;

import com.workbot.workbot.telegram.handle.executor.HandlerExecutor;
import com.workbot.workbot.telegram.setup.intent.UpdateIntent;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DefaultEntrypoint implements HandlerEntrypoint {
    @Autowired
    private List<HandlerExecutor<?>> executors;


    private Map<Class<? extends UpdateIntent>, List<HandlerExecutor<UpdateIntent>>> intentMap;

    @PostConstruct
    public void map() {
        intentMap = new HashMap<>();

        for (var executor : executors) {
            var cl = executor.getIntentType();

            var list = intentMap.computeIfAbsent(cl, k -> new ArrayList<>());

            list.add((HandlerExecutor<UpdateIntent>)executor);
        }
    }

    @Override
    public void handle(UpdateIntent updateIntent) throws TelegramApiException {
        var executorList = intentMap.get(updateIntent.getClass());

        if (executorList == null) {
            throw new UnsupportedOperationException("There is no handlers for that intent type");
        }

        for (var executor : executorList) {
            if (executor.supports(updateIntent)) {
                executor.execute(updateIntent);
            }
        }
    }
}
