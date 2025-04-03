package com.workbot.workbot.logic.update.section;

import com.workbot.workbot.logic.service.vacancy.VacancyService;
import com.workbot.workbot.logic.update.UpdateEntry;
import com.workbot.workbot.logic.update.section.util.SectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class SectionUpdateEntry implements UpdateEntry {
    @Autowired
    SectionProvider provider;

    @Autowired
    VacancyService vacancyService;

    @Override
    @Scheduled(fixedRate = 60000 * 60)
    public void update() {
        var parsers = provider.getAll();

        for (var parser : parsers) {
            var vacancies = parser.parse();

            vacancyService.acceptUpdate(vacancies, parser.getArea(), parser.getCompany());
        }
    }
}
