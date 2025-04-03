package com.workbot.workbot.telegram.handle.handler.filter.state;

import com.workbot.workbot.data.model.dto.util.TelegramSafeString;
import com.workbot.workbot.telegram.handle.handler.filter.FilterState;
import com.workbot.workbot.telegram.handle.handler.util.CancelUtil;
import com.workbot.workbot.telegram.setup.context.data.FilterCacheData;
import com.workbot.workbot.telegram.setup.intent.CallbackUpdateIntent;
import com.workbot.workbot.telegram.setup.intent.DelegatedMessageUpdateIntent;
import com.workbot.workbot.telegram.setup.intent.MessageUpdateIntent;
import com.workbot.workbot.telegram.setup.intent.TextMessageUpdateIntent;
import com.workbot.workbot.telegram.setup.intent.type.CallbackType;
import com.workbot.workbot.telegram.setup.redis.ChatDelegatedMessagesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class KeywordsFilterStateHandler extends FilterStateHandler {
    @Autowired
    private ChatDelegatedMessagesRepo chatDelegatedMessagesRepo;

    @Override
    public void handle(FilterCacheData filterCacheData, MessageUpdateIntent intent) throws TelegramApiException {
        if (intent instanceof DelegatedMessageUpdateIntent delegatedIntent
                && delegatedIntent.getInner() instanceof TextMessageUpdateIntent textInnerIntent) {
            var keywords = Arrays.stream(textInnerIntent.getText().split(","))
                    .map(String::trim).collect(Collectors.toSet());

            filterCacheData.getFilterDto().setKeywords(keywords);

            DeleteMessage deleteUserMessage = DeleteMessage
                    .builder()
                    .chatId(textInnerIntent.getUserId())
                    .messageId(textInnerIntent.getMessageId())
                    .build();

            DeleteMessage deleteBotMessage = DeleteMessage
                    .builder()
                    .chatId(delegatedIntent.getUserId())
                    .messageId(delegatedIntent.getSourceMessageId())
                    .build();

            SendMessage newMessage = generateNewMessage(delegatedIntent.getUserId(), keywords);

            telegramClient.execute(deleteUserMessage);

            telegramClient.execute(deleteBotMessage);

            var result = telegramClient.execute(newMessage);

            delegatedIntent.setSourceMessageId(result.getMessageId());

            chatDelegatedMessagesRepo.save(intent.getUserId(), result.getMessageId());

        } else if (intent instanceof CallbackUpdateIntent callbackUpdateIntent && callbackUpdateIntent.getType() == CallbackType.CONFIRM_KEYWORDS) {
            chatDelegatedMessagesRepo.flush(callbackUpdateIntent.getUserId());

            stateSwitcher.switchState(FilterState.DATE, filterCacheData, intent);
        }
    }

    @Override
    public void init(FilterCacheData filterCacheData, MessageUpdateIntent intent) throws TelegramApiException {
        var message = generateMessage(intent.getUserId(), intent.getMessageId(), Set.of());

        telegramClient.execute(message);

        chatDelegatedMessagesRepo.save(intent.getUserId(), intent.getMessageId());
    }

    private EditMessageText generateMessage(long chatId, int messageId, Set<String> keywords) {
        return EditMessageText
                .builder()
                .text(generateText(generateKeywordsList(keywords)))
                .replyMarkup(getMarkup())
                .messageId(messageId)
                .chatId(chatId)
                .parseMode(ParseMode.MARKDOWNV2)
                .build();
    }

    public SendMessage generateNewMessage(long chatId, Set<String> keywords) {
        return SendMessage
                .builder()
                .text(generateText(generateKeywordsList(keywords)))
                .replyMarkup(getMarkup())
                .chatId(chatId)
                .parseMode(ParseMode.MARKDOWNV2)
                .build();
    }

    private InlineKeyboardMarkup getMarkup() {
        return new InlineKeyboardMarkup(
                List.of(
                        new InlineKeyboardRow(
                                InlineKeyboardButton
                                        .builder()
                                        .text("Перейти далее")
                                        .callbackData(CallbackType.CONFIRM_KEYWORDS.toString())
                                        .build()
                        ),
                        CancelUtil.createCancelRow()
                )
        );
    }

    private String generateText(String keywordList) {
        return """
                    *Создание фильтра*
                    Введите *ключевые слова* для поиска в Описании / Названии вакансий\\.
                    Пример: "Java Core,Spring" создает ключевые слова:
                    1\\. Java Core
                    2\\. Spring
                    Ключевые слова работает в формате логического *ИЛИ*
                    *Текущие ключевые слова*
                    %s
                    """.formatted(keywordList);
    }

    private String generateKeywordsList(Set<String> keywords) {
        if (keywords.isEmpty()) {
            return "_Не заданы_";
        }

        StringBuilder stringBuilder = new StringBuilder();

        int i = 1;

        for (var keyword : keywords) {
            stringBuilder.append(i).append("\\. ");

            stringBuilder.append(TelegramSafeString.escapeMarkdownV2(keyword));

            stringBuilder.append("\n");

            i++;
        }

        return stringBuilder.toString();
    }
}
