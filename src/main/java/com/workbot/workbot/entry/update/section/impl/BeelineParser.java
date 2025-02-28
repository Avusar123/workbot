package com.workbot.workbot.entry.update.section.impl;

import com.workbot.workbot.entry.update.section.SectionParser;
import com.workbot.workbot.model.Area;
import com.workbot.workbot.model.Company;
import com.workbot.workbot.model.Vacancy;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class BeelineParser implements SectionParser {
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
