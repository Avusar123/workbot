package com.workbot.workbot.service.vacancy;

import com.workbot.workbot.model.Area;
import com.workbot.workbot.model.Company;
import com.workbot.workbot.model.Filter;
import com.workbot.workbot.model.Vacancy;
import com.workbot.workbot.repo.VacancyRepo;
import com.workbot.workbot.repo.criteria.VacancyByFilterSpecification;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.Min;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Validated
public class DefaultVacancyService implements VacancyService {
    @Autowired
    VacancyRepo repo;

    @Override
    public void acceptUpdate(Set<Vacancy> vacancies, Area area, Company company) {
        var dbVacancies = repo.findAllByAreaAndCompany(area, company);

        var newSet = new HashSet<>(vacancies);

        var removedSet = new HashSet<>(dbVacancies);

        newSet.removeAll(removedSet);

        removedSet.removeAll(newSet);

        repo.saveAll(newSet);

        repo.deleteAll(removedSet);
    }

    @Override
    public List<Vacancy> getAllBy(Filter filter,
                                  @Positive int maxOnPage, @PositiveOrZero int page) {
        return repo.findAll(
                new VacancyByFilterSpecification(filter),
                PageRequest.of(page, maxOnPage)).getContent();
    }

    @Override
    public Vacancy getById(UUID id) {
        return repo.findById(id).orElseThrow();
    }
}
