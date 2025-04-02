package com.workbot.workbot.telegram.handle.handler;

import com.workbot.workbot.data.model.Subscription;
import com.workbot.workbot.data.model.dto.FilterDto;
import com.workbot.workbot.data.model.dto.SubscriptionDto;
import com.workbot.workbot.logic.service.sub.SubService;
import com.workbot.workbot.telegram.handle.handler.filter.FilterHandler;
import com.workbot.workbot.telegram.handle.handler.util.CancelUtil;
import com.workbot.workbot.telegram.handle.handler.util.FormatUtil;
import com.workbot.workbot.telegram.setup.context.data.SubCacheData;
import com.workbot.workbot.telegram.setup.intent.MessageUpdateIntent;
import com.workbot.workbot.telegram.setup.intent.type.HandlerType;
import com.workbot.workbot.telegram.setup.redis.ChatDelegatedMessagesRepo;
import com.workbot.workbot.telegram.setup.redis.DataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
public class CreateSubHandler {
    @Autowired
    private FilterHandler filterHandler;

    @Autowired
    private OkHttpTelegramClient telegramClient;

    @Autowired
    private ChatDelegatedMessagesRepo chatDelegatedMessagesRepo;

    @Autowired
    private SubService subService;

    @Autowired
    private ShowSubHandler showSubHandler;

    @Autowired
    private DataRepo dataRepo;

    public void init(MessageUpdateIntent intent) throws TelegramApiException {
        filterHandler.create(intent, HandlerType.CREATE_SUB);
    }

    public void afterFilter(long userId, int messageId, FilterDto filterDto) throws TelegramApiException {
        telegramClient.execute(EditMessageText
                        .builder()
                        .text("""
                                *Создание подписки*
                                Введите название подписки \\(максимум 24 символа\\)
                                """)
                        .parseMode(ParseMode.MARKDOWNV2)
                        .replyMarkup(
                                new InlineKeyboardMarkup(
                                        List.of(CancelUtil.createCancelRow())
                                )
                        )
                        .chatId(userId)
                        .messageId(messageId)
                        .build());

        dataRepo.save(userId, messageId, new SubCacheData(new SubscriptionDto(filterDto)));

        chatDelegatedMessagesRepo.save(userId, messageId);
    }

    public void processTitle(String title, SubCacheData subCacheData, long userId, int sourceMessageId) throws TelegramApiException {
        title = FormatUtil.escapeMarkdownV2(title.trim());

        if (title.length() > 24) {
            telegramClient.execute(
                    SendMessage
                            .builder()
                            .text("Ошибка! Название не должно быть больше 24 символов")
                            .chatId(userId)
                            .build()
            );

            return;
        }

        chatDelegatedMessagesRepo.flush(userId);

        var sub = subCacheData.getSubscriptionDto();

        sub.setTitle(title);

        sub.setUserId(userId);

        var id = subService.add(sub);

        showSubHandler.showNew(id, userId);
    }
}
