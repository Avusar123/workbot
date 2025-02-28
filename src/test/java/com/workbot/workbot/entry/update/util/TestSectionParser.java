package com.workbot.workbot.entry.update.util;

import com.workbot.workbot.entry.update.section.SectionParser;
import com.workbot.workbot.model.Area;
import com.workbot.workbot.model.Company;
import com.workbot.workbot.model.Vacancy;

import java.util.Set;

public class TestSectionParser implements SectionParser {

    @Override
    public Set<Vacancy> parse() {
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
