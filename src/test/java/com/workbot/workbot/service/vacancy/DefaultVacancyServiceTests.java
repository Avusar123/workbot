package com.workbot.workbot.service.vacancy;

import com.workbot.workbot.data.model.Area;
import com.workbot.workbot.data.model.Company;
import com.workbot.workbot.data.model.Vacancy;
import com.workbot.workbot.data.model.dto.VacancyDto;
import com.workbot.workbot.data.repo.VacancyRepo;
import com.workbot.workbot.logic.service.vacancy.DefaultVacancyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class DefaultVacancyServiceTests {
    @Mock
    private VacancyRepo vacancyRepo;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private DefaultVacancyService vacancyService;

    private final List<Vacancy> vacancies = List.of(
            new Vacancy("Test", "Test", "Test", LocalDateTime.now(), Area.IT, Company.BEELINE)
    );

    @BeforeEach
    void setUp() {
        Mockito.when(vacancyRepo.findAllByAreaAndCompany(Mockito.any(), Mockito.any()))
                .thenReturn(vacancies);
    }

    @Test
    void acceptUpdate_success() {

        var newSet = Set.of(new VacancyDto(
                "Test 2",
                "Test 2",
                "Test 2",
                LocalDateTime.now(), Area.IT, Company.BEELINE));

        vacancyService.acceptUpdate(newSet, Area.IT, Company.BEELINE);

        Mockito.verify(vacancyRepo).saveAll(Mockito.anyCollection());

        Mockito.verify(eventPublisher).publishEvent(Mockito.any());

        Mockito.verify(vacancyRepo).deleteAll(new HashSet<>(vacancies));
    }
}
