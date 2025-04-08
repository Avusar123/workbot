package com.workbot.workbot.telegram.process;

import com.workbot.workbot.logic.update.section.util.UpdateStatusHolder;
import com.workbot.workbot.telegram.handle.HandlerEntrypoint;
import com.workbot.workbot.telegram.handle.handler.CancelOperationHandler;
import com.workbot.workbot.telegram.setup.extractor.Extractor;
import com.workbot.workbot.telegram.setup.intent.*;
import org.jetbrains.annotations.NotNull;
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
public class TelegramProcessor {
    private static final Logger log = LoggerFactory.getLogger(TelegramProcessor.class);
    @Autowired
    private Extractor<UpdateIntent> updateIntentExtractor;

    @Autowired
    private HandlerEntrypoint handlerEntrypoint;

    @Autowired
    private OkHttpTelegramClient telegramClient;

    @Autowired
    private CancelOperationHandler cancelOperationHandler;

    @Autowired
    private UpdateStatusHolder updateStatusHolder;

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

            if ((intent instanceof CallbackUpdateIntent || intent instanceof PaginationUpdateIntent) && updateStatusHolder.isProcessing()) {
                AnswerCallbackQuery answerCallbackQuery = getAnswerCallbackQuery(intent);

                telegramClient.execute(answerCallbackQuery);

                updateStatusHolder.addUser(((MessageUpdateIntent) intent).getUserId());

                return;
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

    @NotNull
    private AnswerCallbackQuery getAnswerCallbackQuery(UpdateIntent intent) {
        String  queryId;

        if (intent instanceof CallbackUpdateIntent callbackUpdateIntent) {
            queryId = callbackUpdateIntent.getQueryId();
        } else {
            PaginationUpdateIntent paginationUpdateIntent = (PaginationUpdateIntent) intent;
            queryId = paginationUpdateIntent.getQueryId();
        }

        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery(queryId);

        answerCallbackQuery.setText(getTimeoutMessage());

        answerCallbackQuery.setShowAlert(true);
        return answerCallbackQuery;
    }

    private String getTimeoutMessage() {
        return "Бот приостановлен на время обновления вакансий! Сообщим, когда обновление завершится в этом чате.";
    }
}
