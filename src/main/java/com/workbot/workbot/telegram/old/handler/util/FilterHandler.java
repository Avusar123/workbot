package com.workbot.workbot.telegram.old.handler.util;

import com.workbot.workbot.data.model.dto.FilterDto;
import com.workbot.workbot.logic.update.section.util.SectionProvider;
import com.workbot.workbot.telegram.newapi.redis.details.PendingFilterCacheDetails;
import com.workbot.workbot.telegram.newapi.redis.model.PendingModel;
import com.workbot.workbot.telegram.newapi.redis.ModelRepo;
import com.workbot.workbot.telegram.old.event.cache.FilterRequiredEvent;
import com.workbot.workbot.telegram.old.event.update.CallbackRecieved;
import com.workbot.workbot.telegram.old.event.update.CallbackType;
import com.workbot.workbot.telegram.old.holder.PendingContextHolder;
import com.workbot.workbot.telegram.old.holder.UserContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class FilterHandler {
    @Autowired
    private OkHttpTelegramClient okHttpTelegramClient;

    @Autowired
    private SectionProvider provider;

    @Autowired
    private UserContextHolder userContextHolder;

    @Autowired
    private PendingContextHolder pendingContextHolder;

    @Autowired
    private ModelRepo pendingModelRepo;

    private static final int TOTAL_STEPS = 4;

    @EventListener
    public void callbackListener(CallbackRecieved event) throws TelegramApiException {
        var messageId = event.getUpdate().getCallbackQuery().getMessage().getMessageId();

        var userId = userContextHolder.getId();

        if (pendingContextHolder.has()) {
            processStateMachine(pendingModelRepo.get(userId, messageId), event.getUpdate(), userId, messageId);
        }
    }

    @EventListener
    public void create(FilterRequiredEvent event) throws TelegramApiException {
        var details = new PendingFilterCacheDetails(
                new FilterDto()
        );

        var model = new PendingModel(event.getSender(), details);

        processStateMachine(model, event.getUpdate(), userContextHolder.getId(), event.getMessageId());
    }

    private void resolveArea(PendingModel model, Update update, long userId,  int messageId) throws TelegramApiException {
        if ()

        okHttpTelegramClient.execute(EditMessageText
                .builder()
                .text("""
                                *Создание фильтра*
                                Выберите направление из списка
                                """)
                .parseMode(ParseMode.MARKDOWNV2)
                .replyMarkup(
                        new InlineKeyboardMarkup(
                                provider.getAllAreas().stream().map(
                                        area -> new InlineKeyboardRow(InlineKeyboardButton
                                                .builder()
                                                .text(area.getDisplayName())
                                                .callbackData(CallbackType.CHOOSE_AREA + " " + area)
                                                .build())
                                ).toList()
                        )
                )
                .messageId(messageId)
                .chatId(userId)
                .build());
    }

    private void processStateMachine(PendingModel model, Update update, long userId,  int messageId) throws TelegramApiException {
        switch (model.getCurrentStep()) {
            case 0: {
                resolveArea(model, update, userId, messageId);
                break;
            }
            default:
                EditMessageText message = EditMessageText
                        .builder()
                        .messageId(messageId)
                        .chatId(userId)
                        .text("""
                                Произошла ошибка! Вы не должны были достичь этого состояния, пожалуйста свяжитесь с администратором.
                                """)
                        .build();

                okHttpTelegramClient.execute(message);
                break;
        }

        pendingModelRepo.save(userId, messageId, model);
    }
}
