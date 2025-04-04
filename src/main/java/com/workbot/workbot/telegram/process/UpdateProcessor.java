package com.workbot.workbot.telegram.process;

import com.workbot.workbot.telegram.handle.HandlerEntrypoint;
import com.workbot.workbot.telegram.handle.handler.CancelOperationHandler;
import com.workbot.workbot.telegram.setup.extractor.Extractor;
import com.workbot.workbot.telegram.setup.intent.DelegatedMessageUpdateIntent;
import com.workbot.workbot.telegram.setup.intent.PaginationUpdateIntent;
import com.workbot.workbot.telegram.setup.intent.TextMessageUpdateIntent;
import com.workbot.workbot.telegram.setup.intent.UpdateIntent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

@Component
public class UpdateProcessor {
    private static final Logger log = LoggerFactory.getLogger(UpdateProcessor.class);
    @Autowired
    private Extractor<UpdateIntent> updateIntentExtractor;

    @Autowired
    private HandlerEntrypoint handlerEntrypoint;

    @Autowired
    private OkHttpTelegramClient telegramClient;

    @Autowired
    private CancelOperationHandler cancelOperationHandler;

    public void process(Update update) throws TelegramApiException {
        try {
            var intent = updateIntentExtractor.extract(update);

            if (intent instanceof PaginationUpdateIntent pagIntent) {
                if (pagIntent.getTargetPage() == pagIntent.getPaginationContext().getCurrentPage()) {
                    return;
                }
            }

            if (intent instanceof DelegatedMessageUpdateIntent delegatedMessageUpdateIntent
                    && delegatedMessageUpdateIntent.getInner() instanceof TextMessageUpdateIntent textMessageUpdateIntent
                    && textMessageUpdateIntent.getText().startsWith("/")) {

                cancelOperationHandler.cancel(delegatedMessageUpdateIntent.getUserId(), delegatedMessageUpdateIntent.getSourceMessageId());

                intent = textMessageUpdateIntent;
            }


            try {
                handlerEntrypoint.handle(intent);
            } catch (TelegramApiRequestException requestException) {
                if (requestException.getErrorCode() == 400 && intent instanceof DelegatedMessageUpdateIntent delegatedMessageUpdateIntent) {
                    cancelOperationHandler.cancel(delegatedMessageUpdateIntent.getUserId(), delegatedMessageUpdateIntent.getSourceMessageId());
                } else {
                    throw requestException;
                }
            }

        } catch (IllegalArgumentException ex) {
            log.error(ex.getMessage());

            if (update.hasCallbackQuery()) {
                telegramClient.execute(new AnswerCallbackQuery(update.getCallbackQuery().getId(),
                        "Похоже, что срок жизни этого сообщения истек либо произошла непредвиденная ошибка с аргументами!",
                        true,
                        null,
                        5));
            }
        }
    }
}
