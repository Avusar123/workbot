package com.workbot.workbot.logic.update.section.impl;

import com.workbot.workbot.data.model.Area;
import com.workbot.workbot.data.model.Company;
import com.workbot.workbot.data.model.dto.VacancyDto;
import com.workbot.workbot.logic.update.section.SectionParser;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.Set;

@Service
public class BeelineParser implements SectionParser {
    @Override
    public Set<VacancyDto> parse() {
        var random = new Random();

        return Set.of(
                new VacancyDto(
                        "Тест " + random.nextInt(),
                        "Тест",
                        "https://google.com",
                        LocalDateTime.now(),
                        Area.IT,
                        Company.BEELINE
                )
        );
    }

    @Override
    public Area getArea() {
        return Area.IT;
    }

    @Override
    public Company getCompany() {
        return Company.BEELINE;
    }
}
