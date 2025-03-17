package com.workbot.workbot.logic.update.section;

import com.workbot.workbot.logic.update.UpdateEntry;
import com.workbot.workbot.logic.update.section.util.SectionProvider;
import com.workbot.workbot.logic.service.vacancy.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SectionUpdateEntry implements UpdateEntry {
    @Autowired
    SectionProvider provider;

    @Autowired
    VacancyService vacancyService;

    @Override
    public void update() {
        var parsers = provider.getAll();

        for (var parser : parsers) {
            var vacancies = parser.parse();

            vacancyService.acceptUpdate(vacancies, parser.getArea(), parser.getCompany());
        }
    }
}
