package com.workbot.workbot.telegram.newapi.setup.extractor;

import com.workbot.workbot.telegram.newapi.setup.intent.PaginationUpdateIntent;
import com.workbot.workbot.telegram.newapi.setup.intent.type.CallbackType;
import com.workbot.workbot.telegram.newapi.setup.redis.PaginationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class PaginationIntentExtractor implements Extractor<PaginationUpdateIntent> {
    @Autowired
    private PaginationRepo paginationRepo;

    @Override
    public boolean has(Update update) {
        return update.hasCallbackQuery() &&
                CallbackType.valueOf(
                        update
                                .getCallbackQuery()
                                .getData()
                                .split(" ")[0]
                ) == CallbackType.PAGINATION &&
                update
                        .getCallbackQuery()
                        .getData()
                        .split(" ")
                        .length == 2;
    }

    @Override
    public PaginationUpdateIntent extract(Update update) {
        var data = update.getCallbackQuery().getData();

        var dataSplitted = data.split(" ", 2);

        var targetPage = Integer.parseInt(dataSplitted[1]);

        var messageId = update.getCallbackQuery().getMessage().getMessageId();

        var userId = update.getCallbackQuery().getFrom().getId();

        if (!paginationRepo.contains(userId, messageId)) {
            throw new IllegalArgumentException("This message does not contains pagination");
        }

        var context = paginationRepo.get(userId, messageId);

        return new PaginationUpdateIntent(
                update.getCallbackQuery().getMessage().getMessageId(),
                update.getCallbackQuery().getFrom().getId(),
                context,
                targetPage
        );
    }
}
