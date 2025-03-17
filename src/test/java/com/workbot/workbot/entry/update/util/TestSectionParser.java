package com.workbot.workbot.entry.update.util;

import com.workbot.workbot.data.model.Area;
import com.workbot.workbot.data.model.Company;
import com.workbot.workbot.data.model.dto.VacancyDto;
import com.workbot.workbot.logic.update.section.SectionParser;

import java.util.Set;

public class TestSectionParser implements SectionParser {

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
        return Company.BEELINE;
    }
}
