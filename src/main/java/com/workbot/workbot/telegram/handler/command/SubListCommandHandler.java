package com.workbot.workbot.telegram.handler.command;

import com.workbot.workbot.data.model.dto.SubscriptionDto;
import com.workbot.workbot.logic.service.user.UserService;
import com.workbot.workbot.telegram.cache.repo.PaginationRepo;
import com.workbot.workbot.telegram.event.update.CallbackType;
import com.workbot.workbot.telegram.event.cache.PaginationCalledEvent;
import com.workbot.workbot.telegram.event.update.TextMessageRecieved;
import com.workbot.workbot.telegram.cache.pagination.PaginationModel;
import com.workbot.workbot.telegram.cache.pagination.PaginationRowBuilder;
import com.workbot.workbot.telegram.cache.pagination.Paginations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SubListCommandHandler extends CommandHandler {
    @Autowired
    private UserService userService;

    @Autowired
    private PaginationRepo paginationRepo;

    @Override
    protected void work(TextMessageRecieved event) throws TelegramApiException {
        paginationListener(new PaginationCalledEvent(
                this,
                event.getUpdate(),
                null,
                0));
    }

    @EventListener
    public void paginationListener(PaginationCalledEvent event) throws TelegramApiException {
        var user = userContextHolder.getCurrent();

        var subs = userService.getSubs(userContextHolder.getId(), Paginations.PAGE_SIZE, event.getTargetPage());

        var rows = generateRows(subs);

        if (subs.hasNext() || subs.hasPrevious()) {
            rows.add(PaginationRowBuilder.from(subs));
        }

        var model = event.getModel();

        int messageId;

        if (model == null) {
            model = new PaginationModel("show-subs", event.getTargetPage(), Paginations.PAGE_SIZE);

            var action = buildMessage(
                    rows,
                    user.getId(),
                    (int) subs.getTotalElements(),
                    event.getTargetPage(),
                    subs.getTotalPages());

            var result = okHttpTelegramClient.execute(action);

            messageId = result.getMessageId();

        } else {
            model.setCurrentPage(event.getTargetPage());

            messageId = event.getUpdate().getCallbackQuery().getMessage().getMessageId();

            var action = buildEditMessage(
                    rows,
                    user.getId(),
                    messageId,
                    (int) subs.getTotalElements(),
                    event.getTargetPage(),
                    subs.getTotalPages());

            okHttpTelegramClient.execute(action);
        }

        paginationRepo.save(user.getId(), messageId, model);
    }

    private List<InlineKeyboardRow> generateRows(Page<SubscriptionDto> subs) {
        var buttons = subs.stream().map(
                sub -> InlineKeyboardButton
                        .builder()
                        .callbackData(CallbackType.SHOW_SUB + " " + sub.getId())
                        .text(sub.getTitle())
                        .build()
        ).collect(Collectors.toCollection(() -> new ArrayList<InlineKeyboardButton>()));

        buttons.add(InlineKeyboardButton
                .builder()
                .text("Создать подписку")
                .callbackData(CallbackType.CREATE_SUB.toString())
                .build());

        return new ArrayList<>(buttons.stream().map(
                InlineKeyboardRow::new).toList());
    }

    private EditMessageText buildEditMessage(List<InlineKeyboardRow> rows,
                                             long userId,
                                             int messageId,
                                             int subsCount,
                                             int currentPage,
                                             int totalPage) {
        return EditMessageText
                .builder()
                .chatId(userId)
                .messageId(messageId)
                .text(("""
                        **Ваши подписки**
                        Всего у вас *%d* подписок
                        Текущая страница *%d* из *%d*
                        """).formatted(subsCount, currentPage + 1, totalPage))
                .parseMode(ParseMode.MARKDOWNV2)
                .replyMarkup(InlineKeyboardMarkup
                        .builder()
                        .keyboard(rows)
                        .build())
                .build();
    }

    private SendMessage buildMessage(List<InlineKeyboardRow> rows,
                                     long userId,
                                     int subsCount,
                                     int currentPage,
                                     int totalPage) {
        return SendMessage
                .builder()
                .chatId(userId)
                .text(("""
                        **Ваши подписки**
                        Всего у вас *%d* подписок
                        Текущая страница *%d* из *%d*
                        """).formatted(subsCount, currentPage + 1, totalPage))
                .parseMode(ParseMode.MARKDOWNV2)
                .replyMarkup(InlineKeyboardMarkup
                        .builder()
                        .keyboard(rows)
                        .build())
                .build();
    }

    @Override
    protected String getPrefix() {
        return "";
    }

    @Override
    protected String getTriggerCommand() {
        return "подписки";
    }
}
