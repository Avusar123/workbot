package com.workbot.workbot.logic.update.section;

import com.workbot.workbot.logic.service.vacancy.VacancyService;
import com.workbot.workbot.logic.update.UpdateEntry;
import com.workbot.workbot.logic.update.section.util.ParserException;
import com.workbot.workbot.logic.update.section.util.SectionProvider;
import com.workbot.workbot.logic.update.section.util.UpdateStatusHolder;
import com.workbot.workbot.telegram.setup.event.CustomIntentEvent;
import com.workbot.workbot.telegram.setup.intent.AdminNotifyIntent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SectionUpdateEntry implements UpdateEntry {
    private static final Logger log = LoggerFactory.getLogger(SectionUpdateEntry.class);
    @Autowired
    SectionProvider provider;

    @Autowired
    VacancyService vacancyService;

    @Autowired
    ApplicationEventPublisher publisher;

    @Autowired
    UpdateStatusHolder updateStatusHolder;

    @Override
    @Scheduled(fixedRate = 60000 * 60)
    public void update() {
        var parsers = provider.getAll();

        updateStatusHolder.setProcessing(true);

        for (var parser : parsers) {
            log.trace("Start parsing company {} with area {} by parser {}", parser.getCompany(), parser.getArea(), parser.getClass().getName());
            try {
                var vacancies = parser.parse();

                vacancyService.acceptUpdate(vacancies, parser.getArea(), parser.getCompany());
            } catch (Exception ex) {
                log.error(Marker.ANY_MARKER, ex.getMessage(), ex);

                publisher.publishEvent(new CustomIntentEvent(
                        this,
                        new AdminNotifyIntent(ex.getMessage())));
            }


            log.info("Ended parsing company {} with area {} by parser {}", parser.getCompany(), parser.getArea(), parser.getClass().getName());

            log.debug("Parse and save vacancies company {} with area {} by parser {}", parser.getCompany(), parser.getArea(), parser.getClass().getName());
        }

        updateStatusHolder.setProcessing(false);
    }
}
