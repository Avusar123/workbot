package com.workbot.workbot.logic.update.section.impl;

import com.workbot.workbot.data.model.Area;
import com.workbot.workbot.data.model.Company;
import com.workbot.workbot.data.model.dto.VacancyDto;
import com.workbot.workbot.logic.update.section.SectionParser;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TinkoffParser implements SectionParser {
    @Override
    public Set<VacancyDto> parse() {
        return Set.of();
    }

    @Override
    public Area getArea() {
        return Area.IT;
    }

    @Override
    public Company getCompany() {
        return Company.TINKOFF;
    }
}
