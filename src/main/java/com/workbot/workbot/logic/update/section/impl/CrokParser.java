package com.workbot.workbot.logic.update.section.impl;

import com.workbot.workbot.data.model.Area;
import com.workbot.workbot.data.model.Company;
import com.workbot.workbot.data.model.dto.VacancyDto;
import com.workbot.workbot.logic.update.section.SectionParser;
import com.workbot.workbot.logic.update.section.util.HttpConstants;
import com.workbot.workbot.logic.update.section.util.ParserException;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class CrokParser implements SectionParser {
    private static final String ALL_VACANCIES_URL = "https://careers.croc.ru/vacancies/";

    private static final String VACANCY_BASE_URL = "https://careers.croc.ru%s";

    private static final String SECTION_VACANCIES_BASE = "https://careers.croc.ru/vacancies/%s";

    @Override
    public Set<VacancyDto> parse() {
        var vacancies = new HashSet<VacancyDto>();

        try {
            var result = Jsoup
                    .connect(ALL_VACANCIES_URL)
                    .userAgent(HttpConstants.USER_AGENT)
                    .get();

            var sectionLists = result.getElementsByClass("vacancy__card-all")
                    .stream()
                    .map(
                            card -> card.attr("href"))
                    .toList();

            for (var sectionList : sectionLists) {
                var sectionResults = Jsoup
                        .connect(SECTION_VACANCIES_BASE.formatted(sectionList))
                        .userAgent(HttpConstants.USER_AGENT)
                        .get();

                var vacLinks = sectionResults
                        .getElementsByClass("vacancy__card-item")
                        .stream()
                        .map(vac -> vac
                                .getElementsByTag("a")
                                .first()
                                .attr("href"))
                        .toList();

                for (var vacLink : vacLinks) {
                    var vacResult = Jsoup
                            .connect(VACANCY_BASE_URL.formatted(vacLink))
                            .userAgent(HttpConstants.USER_AGENT)
                            .get();

                    var main = vacResult.getElementsByClass("vacancy-detail__content-main").first();

                    var title = main.getElementsByTag("h1").first().text();

                    var desc = main.html();

                    vacancies.add(
                            new VacancyDto(
                                    title,
                                    desc,
                                    VACANCY_BASE_URL.formatted(vacLink),
                                    LocalDateTime.now(),
                                    Area.IT,
                                    Company.CROK
                            )
                    );
                }
            }

            return vacancies;

        } catch (Exception e) {
            throw new ParserException(this, e);
        }
    }

    @Override
    public Area getArea() {
        return Area.IT;
    }

    @Override
    public Company getCompany() {
        return Company.CROK;
    }
}
