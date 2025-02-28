package com.workbot.workbot.entry.update.section;

import com.workbot.workbot.model.Area;
import com.workbot.workbot.model.Company;
import com.workbot.workbot.model.Vacancy;

import java.util.Set;

public interface SectionParser {
    Set<Vacancy> parse();

    Area getArea();

    Company getCompany();
}
