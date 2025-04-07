package com.workbot.workbot.logic.update.section.impl;

import com.workbot.workbot.data.model.Area;
import com.workbot.workbot.data.model.Company;
import com.workbot.workbot.data.model.dto.VacancyDto;
import com.workbot.workbot.logic.update.section.SectionParser;
import com.workbot.workbot.logic.update.section.util.HttpConstants;
import com.workbot.workbot.logic.update.section.util.ParserException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CinimexParser implements SectionParser {
    private static final String VACANCIES_URL =  "https://cinimex.ru/vacancies/moscow";

    private static final String BASE_URL = "https://cinimex.ru%s";

    @Override
    public Set<VacancyDto> parse() {
        var vacancies = new HashSet<VacancyDto>();

        try {
            var result = Jsoup
                    .connect(VACANCIES_URL)
                    .userAgent(HttpConstants.USER_AGENT)
                    .get();

            var links = result.getElementsByClass("vacancy-city")
                    .first()
                    .getElementsByTag("a")
                    .stream()
                    .map(l -> l.attr("href"))
                    .toList();

            for (var link : links) {
                var cityResult = Jsoup
                        .connect(BASE_URL.formatted(link))
                        .userAgent(HttpConstants.USER_AGENT)
                        .get();

                var vacLinks = cityResult.getElementsByClass("vacancy")
                        .stream()
                        .map(vac ->
                                vac.getElementsByTag("a")
                                        .first()
                                        .attr("href"))
                        .toList();

                for (var vacLink : vacLinks) {
                    var vacResult = Jsoup
                            .connect(BASE_URL.formatted(vacLink))
                            .userAgent(HttpConstants.USER_AGENT)
                            .get();


                    var title = vacResult.getElementsByTag("h3").first().text();

                    var desc = vacResult
                            .getElementsByTag("section")
                            .select("ul")
                            .stream()
                            .map(Element::html)
                            .collect(Collectors.joining("\n"));

                    vacancies.add(
                            new VacancyDto(
                                    title,
                                    desc,
                                    BASE_URL.formatted(vacLink),
                                    LocalDateTime.now(),
                                    Area.IT,
                                    Company.CINIMEX
                            )
                    );
                }
            }

        } catch (Exception e) {
            throw new ParserException(this, e);
        }

        return vacancies;
    }

    @Override
    public Area getArea() {
        return Area.IT;
    }

    @Override
    public Company getCompany() {
        return Company.CINIMEX;
    }
}
