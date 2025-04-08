package com.workbot.workbot.telegram.handle.handler;

import com.workbot.workbot.data.model.dto.FilterDto;
import com.workbot.workbot.logic.service.vacancy.VacancyService;
import com.workbot.workbot.telegram.handle.handler.filter.FilterHandler;
import com.workbot.workbot.telegram.handle.handler.util.PaginationUtil;
import com.workbot.workbot.telegram.handle.handler.util.VacancyUtil;
import com.workbot.workbot.telegram.setup.context.data.SearchFilterCacheData;
import com.workbot.workbot.telegram.setup.intent.MessageUpdateIntent;
import com.workbot.workbot.telegram.setup.intent.type.CallbackType;
import com.workbot.workbot.telegram.setup.intent.type.HandlerType;
import com.workbot.workbot.telegram.setup.redis.DataRepo;
import com.workbot.workbot.telegram.setup.redis.PaginationContext;
import com.workbot.workbot.telegram.setup.redis.PaginationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
public class SearchHandler {
    @Autowired
    private OkHttpTelegramClient telegramClient;

    @Autowired
    private VacancyService vacancyService;

    @Autowired
    private FilterHandler filterHandler;

    @Autowired
    private DataRepo dataRepo;

    @Autowired
    private PaginationRepo paginationRepo;

    public void init(long userId) throws TelegramApiException {
        telegramClient.execute(
                SendMessage
                        .builder()
                        .text("""
                                Поиск позволяет вам найти все вакансии в нашей базе по фильтру.
                                Чтобы начать воспользуйтесь кнопкой ниже
                                """)
                        .replyMarkup(new InlineKeyboardMarkup(
                                List.of(new InlineKeyboardRow(
                                        InlineKeyboardButton
                                                .builder()
                                                .text("Начать поиск")
                                                .callbackData(CallbackType.INIT_SEARCH.toString())
                                                .build()
                                ))
                        ))
                        .chatId(userId)
                        .build()
        );
    }

    public void process(MessageUpdateIntent intent) throws TelegramApiException {
        filterHandler.create(intent, HandlerType.SEARCH);
    }

    public void handlePage(FilterDto filterDto, long userId, int messageId, int page) throws TelegramApiException {
        var result = vacancyService.getAllBy(filterDto, 1, page);

        if (result.getTotalElements() == 0) {
            telegramClient.execute(
                    EditMessageText
                            .builder()
                            .text("По данному фильтру нет результатов")
                            .chatId(userId)
                            .messageId(messageId)
                            .build()
            );
        } else {
            var vacancy = result.get().findFirst().orElseThrow();

            var rows = new ArrayList<>(VacancyUtil.generateMarkupRows(vacancy));

            rows.add(PaginationUtil.paginationRow(result));

            EditMessageText message = EditMessageText
                    .builder()
                    .text("""
                            *Результаты поиска*
                            %s
                            Страница %d из %d
                            """.formatted(VacancyUtil.formatVacancy(vacancy), result.getNumber() + 1, result.getTotalPages()))
                    .replyMarkup(
                            new InlineKeyboardMarkup(
                                    rows
                            )
                    )
                    .parseMode(ParseMode.MARKDOWNV2)
                    .messageId(messageId)
                    .chatId(userId)
                    .build();

            telegramClient.execute(message);

            paginationRepo.save(userId, messageId, new PaginationContext(page, HandlerType.SEARCH));
        }

        dataRepo.save(userId, messageId, new SearchFilterCacheData(filterDto));
    }
}
