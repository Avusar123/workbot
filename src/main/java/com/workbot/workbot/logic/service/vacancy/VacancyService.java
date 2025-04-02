package com.workbot.workbot.logic.service.vacancy;

import com.workbot.workbot.data.model.Area;
import com.workbot.workbot.data.model.Company;
import com.workbot.workbot.data.model.dto.FilterDto;
import com.workbot.workbot.data.model.dto.VacancyDto;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface VacancyService {
    void acceptUpdate(Set<VacancyDto> vacancies, Area area, Company company);

    Page<VacancyDto> getAllBy(FilterDto filter, @Positive int maxOnPage, @PositiveOrZero int page);

    VacancyDto getById(UUID id);
}
