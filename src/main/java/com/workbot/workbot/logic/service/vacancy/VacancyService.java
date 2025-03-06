package com.workbot.workbot.logic.service.vacancy;

import com.workbot.workbot.data.model.Area;
import com.workbot.workbot.data.model.Company;
import com.workbot.workbot.data.model.dto.FilterDto;
import com.workbot.workbot.data.model.dto.VacancyDto;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface VacancyService {
    void acceptUpdate(Set<VacancyDto> vacancies, Area area, Company company);

    List<VacancyDto> getAllBy(FilterDto filter, int maxOnPage, int page);

    VacancyDto getById(UUID id);
}
