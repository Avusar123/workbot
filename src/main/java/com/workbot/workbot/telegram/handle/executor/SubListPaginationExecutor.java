package com.workbot.workbot.telegram.handle.executor;

import com.workbot.workbot.telegram.handle.handler.SubListHandler;
import com.workbot.workbot.telegram.setup.intent.PaginationUpdateIntent;
import com.workbot.workbot.telegram.setup.intent.type.HandlerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class SubListPaginationExecutor implements HandlerExecutor<PaginationUpdateIntent> {
    @Autowired
    private SubListHandler subListHandler;

    @Override
    public boolean supports(PaginationUpdateIntent intent) {
        return intent.getPaginationContext().getHandlerType() == HandlerType.SUBLIST;
    }

    @Override
    public void execute(PaginationUpdateIntent intent) throws TelegramApiException {
        subListHandler.edit(
                intent.getTargetPage(),
                intent.getUserId(),
                intent.getMessageId());
    }

    @Override
    public Class<PaginationUpdateIntent> getIntentType() {
        return PaginationUpdateIntent.class;
    }
}
