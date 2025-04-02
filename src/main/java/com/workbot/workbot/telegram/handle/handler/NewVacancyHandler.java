package com.workbot.workbot.telegram.handle.handler;

import com.workbot.workbot.data.model.dto.VacancyDto;
import com.workbot.workbot.logic.service.sub.SubService;
import com.workbot.workbot.telegram.handle.handler.util.FormatUtil;
import com.workbot.workbot.telegram.handle.handler.util.VacancyUtil;
import com.workbot.workbot.telegram.setup.intent.type.CallbackType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Collections;

@Component
public class NewVacancyHandler {
    private static final Logger log = LoggerFactory.getLogger(NewVacancyHandler.class);
    @Autowired
    private SubService subService;

    @Autowired
    private OkHttpTelegramClient telegramClient;

    public void process(VacancyDto vacancyDto) {
        var subs = subService.getAllBy(vacancyDto);

        for (var sub : subs) {
            var rows = new ArrayList<>(VacancyUtil.generateMarkupRows(vacancyDto));

            rows.add(new InlineKeyboardRow(
                    InlineKeyboardButton
                            .builder()
                            .text("Перейти к подписке")
                            .callbackData(CallbackType.SHOW_SUB_NEW + " " + sub.getId())
                            .build()
            ));

            SendMessage message = SendMessage
                    .builder()
                    .chatId(sub.getUserId())
                    .text("""
                            *Сработала подписка*
                            *Название:* %s
                            _Вакансия_
                            %s
                            """.formatted(sub.getTitle(), VacancyUtil.formatVacancy(vacancyDto)))
                    .replyMarkup(
                            new InlineKeyboardMarkup(
                                    rows

                            )
                    )
                    .parseMode(ParseMode.MARKDOWNV2)
                    .build();

            try {
                telegramClient.execute(message);
            } catch (TelegramApiException e) {
                log.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
