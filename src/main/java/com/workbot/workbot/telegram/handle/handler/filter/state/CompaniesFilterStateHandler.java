package com.workbot.workbot.telegram.handle.handler.filter.state;

import com.workbot.workbot.data.model.Area;
import com.workbot.workbot.data.model.Company;
import com.workbot.workbot.data.model.dto.FilterDto;
import com.workbot.workbot.logic.update.section.util.SectionProvider;
import com.workbot.workbot.telegram.handle.handler.filter.FilterState;
import com.workbot.workbot.telegram.handle.handler.util.PaginationUtil;
import com.workbot.workbot.telegram.setup.context.data.FilterCacheData;
import com.workbot.workbot.telegram.setup.intent.CallbackUpdateIntent;
import com.workbot.workbot.telegram.setup.intent.MessageUpdateIntent;
import com.workbot.workbot.telegram.setup.intent.PaginationUpdateIntent;
import com.workbot.workbot.telegram.setup.intent.type.CallbackType;
import com.workbot.workbot.telegram.setup.intent.type.HandlerType;
import com.workbot.workbot.telegram.setup.context.PaginationContext;
import com.workbot.workbot.telegram.setup.redis.PaginationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Component
public class CompaniesFilterStateHandler extends FilterStateHandler {
    @Autowired
    private SectionProvider sectionProvider;

    @Autowired
    private PaginationRepo paginationRepo;

    @Override
    public void handle(FilterCacheData filterCacheData, MessageUpdateIntent intent) throws TelegramApiException {
        if (intent instanceof CallbackUpdateIntent callbackUpdateIntent) {
            var filter = filterCacheData.getFilterDto();

            var companies = filter.getCompanies();

            switch (callbackUpdateIntent.getType()) {
                case CallbackType.TOGGLE_COMPANY:
                    var targetCompany = Company.valueOf(callbackUpdateIntent.getArgs());

                    if (companies.contains(targetCompany)) {
                        companies.remove(targetCompany);
                    } else {
                        companies.add(targetCompany);
                    }

                    break;
                case CallbackType.SELECT_COMPANIES:
                    var allCompanies = sectionProvider.getAllCompanies(filter.getArea());

                    if (companies.size() < allCompanies.size()) {
                        filter.setCompanies(allCompanies);
                    } else {
                        filter.setCompanies(new HashSet<>());
                    }

                    break;
                case CallbackType.CONFIRM_COMPANIES:
                    if (companies.isEmpty()) {
                        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery(callbackUpdateIntent.getQueryId());
                        answerCallbackQuery.setText("Необходимо выбрать хоть одну компанию!");

                        telegramClient.execute(answerCallbackQuery);

                        return;
                    }

                    stateSwitcher.switchState(FilterState.KEYWORDS, filterCacheData, intent);

                    return;
            }

            telegramClient.execute(buildMessage(
                    paginationRepo.get(intent.getUserId(), intent.getMessageId()).getCurrentPage(),
                    filterCacheData.getFilterDto(),
                    intent.getUserId(),
                    intent.getMessageId()));

        } else if (intent instanceof PaginationUpdateIntent paginationUpdateIntent) {
            telegramClient.execute(buildMessage(
                    paginationUpdateIntent.getTargetPage(),
                    filterCacheData.getFilterDto(),
                    paginationUpdateIntent.getUserId(),
                    paginationUpdateIntent.getMessageId()));

            var paginationContext = new PaginationContext(paginationUpdateIntent.getTargetPage(), HandlerType.COMPANIES);

            paginationRepo.save(intent.getUserId(), intent.getMessageId(), paginationContext);
        }
    }

    @Override
    public void init(FilterCacheData filterCacheData, MessageUpdateIntent intent) throws TelegramApiException {
        telegramClient.execute(buildMessage(
                0,
                filterCacheData.getFilterDto(),
                intent.getUserId(),
                intent.getMessageId()));

        var paginationContext = new PaginationContext(0, HandlerType.COMPANIES);

        paginationRepo.save(intent.getUserId(), intent.getMessageId(), paginationContext);
    }

    private InlineKeyboardMarkup generateMarkup(Page<Company> companies, Set<Company> selected) {
        var paginationRow = PaginationUtil.paginationRow(companies);

        var contentRows = new ArrayList<>(companies
                .map(co -> new InlineKeyboardRow(
                        InlineKeyboardButton
                                .builder()
                                .text(((selected.contains(co) ? "✅ " : "") + co.getDisplayName()))
                                .callbackData(CallbackType.TOGGLE_COMPANY + " " + co)
                                .build()
                ))
                .toList());

        contentRows.add(
                paginationRow
        );

        contentRows.add(
                new InlineKeyboardRow(
                        InlineKeyboardButton
                                .builder()
                                .text((selected.size() == companies.getTotalElements() ? "✅ " : "") + "Выбрать все")
                                .callbackData(CallbackType.SELECT_COMPANIES.toString())
                                .build()
                )
        );

        contentRows.add(
                new InlineKeyboardRow(
                        InlineKeyboardButton
                                .builder()
                                .text("Подтвердить выбор")
                                .callbackData(CallbackType.CONFIRM_COMPANIES.toString())
                                .build()
                )
        );

        return new InlineKeyboardMarkup(contentRows);
    }

    private EditMessageText buildMessage(int pageNum, FilterDto filterDto, long userId, int messageId) {
        if (filterDto.getArea() == null) {
            throw new IllegalArgumentException("Area is not defined to proceed");
        }

        var page = createPage(pageNum, filterDto.getArea());

        return EditMessageText
                .builder()
                .text("""
                                *Создание фильтра*
                                Выберите компании из списка
                                Страница %d из %d
                                """.formatted(page.getNumber() + 1, page.getTotalPages()))
                .parseMode(ParseMode.MARKDOWNV2)
                .replyMarkup(
                        generateMarkup(page, filterDto.getCompanies())
                )
                .messageId(messageId)
                .chatId(userId)
                .build();

    }

    private Page<Company> createPage(int pageNum, Area area) {
        var pageable = PageRequest.of(pageNum, PaginationUtil.PAGE_SIZE);


        var companies = sectionProvider
                .getAllCompanies(area);

        var pageContent = companies
                .stream()
                .skip(pageable.getOffset())
                .limit(PaginationUtil.PAGE_SIZE)
                .toList();

        return new PageImpl<>(pageContent, pageable, companies.size());
    }
}
