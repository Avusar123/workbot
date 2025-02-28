package com.workbot.workbot.service.vacancy;

import com.workbot.workbot.model.Area;
import com.workbot.workbot.model.Company;
import com.workbot.workbot.model.Vacancy;
import com.workbot.workbot.repo.VacancyRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.Calls;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class DefaultVacancyServiceTests {
    @Mock
    private VacancyRepo vacancyRepo;

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

        var newSet = Set.of(new Vacancy(
                "Test 2",
                "Test 2",
                "Test 2",
                LocalDateTime.now(), Area.IT, Company.BEELINE));

        vacancyService.acceptUpdate(newSet, Area.IT, Company.BEELINE);

        Mockito.verify(vacancyRepo).saveAll(newSet);

        Mockito.verify(vacancyRepo).deleteAll(new HashSet<>(vacancies));
    }
}
