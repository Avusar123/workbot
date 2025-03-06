package com.workbot.workbot.logic.update.section;

import com.workbot.workbot.data.model.Area;
import com.workbot.workbot.data.model.Company;
import com.workbot.workbot.data.model.dto.VacancyDto;

import java.util.Set;

public interface SectionParser {
    Set<VacancyDto> parse();

    Area getArea();

    Company getCompany();
}
