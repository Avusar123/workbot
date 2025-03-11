package com.workbot.workbot.logic.service.vacancy;

import com.workbot.workbot.data.model.Area;
import com.workbot.workbot.data.model.Company;
import com.workbot.workbot.data.model.Vacancy;
import com.workbot.workbot.data.model.dto.FilterDto;
import com.workbot.workbot.data.model.dto.VacancyDto;
import com.workbot.workbot.data.repo.VacancyRepo;
import com.workbot.workbot.data.repo.criteria.VacancyByFilterSpecification;
import com.workbot.workbot.logic.event.NewVacanciesEvent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Validated
public class DefaultVacancyService implements VacancyService {
    @Autowired
    VacancyRepo repo;

    @Autowired
    ApplicationEventPublisher publisher;

    @Override
    public void acceptUpdate(Set<VacancyDto> vacancies, Area area, Company company) {
        var dbVacancies = repo.findAllByAreaAndCompany(area, company);

        var convVac = vacancies.stream().map(Vacancy::new).toList();

        var newSet = new HashSet<>(convVac);

        var removedSet = new HashSet<>(dbVacancies);

        newSet.removeAll(removedSet);

        removedSet.removeAll(newSet);

        repo.saveAll(newSet);

        repo.deleteAll(removedSet);

        publisher.publishEvent(new NewVacanciesEvent(this, newSet.stream().map(VacancyDto::new).collect(Collectors.toSet())));
    }

    @Override
    public List<VacancyDto> getAllBy(FilterDto filter,
                                     @Positive int maxOnPage, @PositiveOrZero int page) {
        return repo.findAll(
                new VacancyByFilterSpecification(filter),
                PageRequest.of(page, maxOnPage))
                    .getContent()
                    .stream()
                    .map(VacancyDto::new)
                    .toList();
    }

    @Override
    public VacancyDto getById(UUID id) {
        return new VacancyDto(repo.findById(id).orElseThrow());
    }
}
