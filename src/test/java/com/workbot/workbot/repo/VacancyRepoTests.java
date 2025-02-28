package com.workbot.workbot.repo;

import com.workbot.workbot.model.Area;
import com.workbot.workbot.model.Company;
import com.workbot.workbot.model.Filter;
import com.workbot.workbot.model.Vacancy;
import com.workbot.workbot.repo.criteria.VacancyByFilterSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class VacancyRepoTests {
    @Autowired
    private VacancyRepo repo;

    @BeforeEach
    void setUp() {
        repo.deleteAll();
    }

    @Test
    void findAllByAreaAndCompany_success() {
        var vacancy = new Vacancy(
                "Test",
                "Test",
                "Test",
                LocalDateTime.now().minusDays(7), Area.IT, Company.BEELINE);

        repo.save(vacancy);

        var result = repo.findAllByAreaAndCompany(Area.IT, Company.BEELINE);

        Assertions.assertEquals(vacancy, result.getFirst());
    }

    @Test
    void findByFilter_success() {
        var vacancy = new Vacancy(
                "Test",
                "Test",
                "Test",
                LocalDateTime.now().minusDays(7), Area.IT, Company.BEELINE);

        repo.save(vacancy);

        var filter = new Filter(
                Set.of("Test"),
                Area.IT,
                Set.of(Company.BEELINE),
                LocalDateTime.now().minusDays(8));

        var newVac = repo.findOne(new VacancyByFilterSpecification(filter)).orElseThrow();

        Assertions.assertEquals(vacancy, newVac);
    }

    @Test
    void findByFilter_olderThanNeeded_empty() {
        var vacancy = new Vacancy(
                "Test",
                "Test",
                "Test",
                LocalDateTime.now().minusDays(7), Area.IT, Company.BEELINE);

        repo.save(vacancy);

        var filter = new Filter(
                Set.of("Test"),
                Area.IT,
                Set.of(Company.BEELINE),
                LocalDateTime.now().minusDays(7).plusHours(1));

        Assertions.assertTrue(repo.findOne(new VacancyByFilterSpecification(filter)).isEmpty());
    }

    @Test
    void findByFilter_embeddedKeyword_success() {
        var vacancy = new Vacancy(
                "Test2",
                "Test2",
                "Test",
                LocalDateTime.now().minusDays(7), Area.IT, Company.BEELINE);

        repo.save(vacancy);

        var filter = new Filter(
                Set.of("Test"),
                Area.IT,
                Set.of(Company.BEELINE),
                LocalDateTime.now().minusDays(8));

        var newVac = repo.findOne(new VacancyByFilterSpecification(filter)).orElseThrow();

        Assertions.assertEquals(vacancy, newVac);
    }

    @Test
    void findByFilter_emptyKeywords_success() {
        var vacancy = new Vacancy(
                "Test2",
                "Test2",
                "Test",
                LocalDateTime.now().minusDays(7), Area.IT, Company.BEELINE);

        repo.save(vacancy);

        var filter = new Filter(
                Set.of(),
                Area.IT,
                Set.of(Company.BEELINE),
                LocalDateTime.now().minusDays(8));

        var newVac = repo.findOne(new VacancyByFilterSpecification(filter)).orElseThrow();

        Assertions.assertEquals(vacancy, newVac);
    }

    @Test
    void findByFilter_differentKeywords_empty() {
        var vacancy = new Vacancy(
                "Test2",
                "Test2",
                "Test",
                LocalDateTime.now().minusDays(7), Area.IT, Company.BEELINE);

        repo.save(vacancy);

        var filter = new Filter(
                Set.of("Test3"),
                Area.IT,
                Set.of(Company.BEELINE),
                LocalDateTime.now().minusDays(8));

        Assertions.assertTrue(repo.findOne(new VacancyByFilterSpecification(filter)).isEmpty());
    }

    @Test
    void findByFilter_onlyInTitle_empty() {
        var vacancy = new Vacancy(
                "Test2",
                "Test3",
                "Test",
                LocalDateTime.now().minusDays(7), Area.IT, Company.BEELINE);

        repo.save(vacancy);

        var filter = new Filter(
                Set.of("Test2"),
                Area.IT,
                Set.of(Company.BEELINE),
                LocalDateTime.now().minusDays(8));

        var newVac = repo.findOne(new VacancyByFilterSpecification(filter)).orElseThrow();

        Assertions.assertEquals(vacancy, newVac);
    }

    @Test
    void findByFilter_onlyInDesc_empty() {
        var vacancy = new Vacancy(
                "Test3",
                "Test2",
                "Test",
                LocalDateTime.now().minusDays(7), Area.IT, Company.BEELINE);

        repo.save(vacancy);

        var filter = new Filter(
                Set.of("Test2"),
                Area.IT,
                Set.of(Company.BEELINE),
                LocalDateTime.now().minusDays(8));

        var newVac = repo.findOne(new VacancyByFilterSpecification(filter)).orElseThrow();

        Assertions.assertEquals(vacancy, newVac);
    }

}
