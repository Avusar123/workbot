package com.workbot.workbot.logic.service.vacancy;

import com.workbot.workbot.data.model.Area;
import com.workbot.workbot.data.model.Company;
import com.workbot.workbot.data.model.Vacancy;
import com.workbot.workbot.data.model.dto.FilterDto;
import com.workbot.workbot.data.model.dto.VacancyDto;
import com.workbot.workbot.data.repo.VacancyRepo;
import com.workbot.workbot.data.repo.criteria.VacancyByFilterSpecification;
import com.workbot.workbot.telegram.setup.event.CustomIntentEvent;
import com.workbot.workbot.telegram.setup.intent.VacanciesUpdateIntent;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Validated
public class DefaultVacancyService implements VacancyService {
    @Autowired
    VacancyRepo repo;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public void acceptUpdate(Set<VacancyDto> vacancies, Area area, Company company) {
        var dbVacancies = repo.findAllByAreaAndCompany(area, company).stream().map(VacancyDto::new).toList();

        var newSet = new HashSet<>(vacancies);

        var removedSet = new HashSet<>(dbVacancies);

        newSet.removeAll(removedSet);

        removedSet.removeAll(vacancies);

        repo.saveAll(newSet.stream().map(Vacancy::new).toList());

        repo.deleteAllById(removedSet.stream().map(VacancyDto::getId).toList());

        eventPublisher.publishEvent(new CustomIntentEvent(
                this,
                new VacanciesUpdateIntent(newSet, removedSet)));
    }

    @Override
    public Page<VacancyDto> getAllBy(FilterDto filter,
                                     @Positive int maxOnPage, @PositiveOrZero int page) {
        var vacancies = repo.findAll(
                new VacancyByFilterSpecification(filter),
                PageRequest.of(page, maxOnPage, Sort.by("added").descending())
        );

        List<VacancyDto> vacancyDtos = vacancies.getContent()
                .stream()
                .map(VacancyDto::new)
                .toList();

        return new PageImpl<>(vacancyDtos, PageRequest.of(page, maxOnPage), vacancies.getTotalElements());
    }

    @Override
    public VacancyDto getById(UUID id) {
        return new VacancyDto(repo.findById(id).orElseThrow());
    }
}
