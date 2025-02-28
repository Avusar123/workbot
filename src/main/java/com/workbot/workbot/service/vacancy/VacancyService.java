package com.workbot.workbot.service.vacancy;

import com.workbot.workbot.model.Area;
import com.workbot.workbot.model.Company;
import com.workbot.workbot.model.Filter;
import com.workbot.workbot.model.Vacancy;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface VacancyService {
    void acceptUpdate(Set<Vacancy> vacancies, Area area, Company company);

    List<Vacancy> getAllBy(Filter filter, int maxOnPage, int page);

    Vacancy getById(UUID id);
}
