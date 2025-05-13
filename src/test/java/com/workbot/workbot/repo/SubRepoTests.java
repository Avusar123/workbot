package com.workbot.workbot.repo;

import com.workbot.workbot.data.model.Area;
import com.workbot.workbot.data.model.Company;
import com.workbot.workbot.data.model.Filter;
import com.workbot.workbot.data.model.Subscription;
import com.workbot.workbot.data.model.dto.VacancyDto;
import com.workbot.workbot.data.repo.SubRepo;
import com.workbot.workbot.data.repo.criteria.SubByVacancySpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Set;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = "spring.liquibase.contexts=dev")
public class SubRepoTests {
    @Autowired
    private SubRepo repo;

    @BeforeEach
    void setUp() {
        repo.deleteAll();
    }

    @Test
    void findByVacancy_success() {
        var vacancy = new VacancyDto(
                "Test123",
                "Test123",
                "Test",
                LocalDateTime.now().minusDays(20), Area.IT, Company.BEELINE);

        var filter = new Filter(
                Set.of("Test123"),
                Area.IT, Set.of(Company.BEELINE),
                LocalDateTime.now().minusDays(30));

        var sub = new Subscription("NewSub", filter);

        repo.save(sub);

        var dbSub = repo.findOne(new SubByVacancySpecification(vacancy)).orElseThrow();

        Assertions.assertEquals(sub, dbSub);
    }

    @Test
    void findByVacancy_olderThanNeeded_empty() {
        var vacancy = new VacancyDto(
                "Test123",
                "Test123",
                "Test",
                LocalDateTime.now().minusDays(20), Area.IT, Company.BEELINE);

        var filter = new Filter(
                Set.of("Test123"),
                Area.IT, Set.of(Company.BEELINE),
                LocalDateTime.now().minusDays(19));

        var sub = new Subscription("NewSub", filter);

        repo.save(sub);

        Assertions.assertTrue(repo.findOne(new SubByVacancySpecification(vacancy)).isEmpty());
    }

    @Test
    void findByVacancy_embeddedKeyword_success() {
        var vacancy = new VacancyDto(
                "sfsgrgsfdrgtest123dfsgrgsdfgsr",
                "sdrgsdfgrtest123sdfgsrgsdf",
                "Test",
                LocalDateTime.now().minusDays(20), Area.IT, Company.BEELINE);

        var filter = new Filter(
                Set.of("Test123"),
                Area.IT, Set.of(Company.BEELINE),
                LocalDateTime.now().minusDays(30));

        var sub = new Subscription("NewSub", filter);

        repo.save(sub);

        var dbSub = repo.findOne(new SubByVacancySpecification(vacancy)).orElseThrow();

        Assertions.assertEquals(sub, dbSub);
    }

    @Test
    void findByVacancy_emptyKeywords_success() {
        var vacancy = new VacancyDto(
                "sfsgrgsfdrgtest123dfsgrgsdfgsr",
                "sdrgsdfgrtest123sdfgsrgsdf",
                "Test",
                LocalDateTime.now().minusDays(20), Area.IT, Company.BEELINE);

        var filter = new Filter(
                Set.of(),
                Area.IT, Set.of(Company.BEELINE),
                LocalDateTime.now().minusDays(30));

        var sub = new Subscription("NewSub", filter);

        repo.save(sub);

        var dbSub = repo.findOne(new SubByVacancySpecification(vacancy)).orElseThrow();

        Assertions.assertEquals(sub, dbSub);
    }

    @Test
    void findByVacancy_differentKeywords_empty() {
        var vacancy = new VacancyDto(
                "sfsgrgsfdrgtest123dfsgrgsdfgsr",
                "sdrgsdfgrtest123sdfgsrgsdf",
                "Test",
                LocalDateTime.now().minusDays(20), Area.IT, Company.BEELINE);

        var filter = new Filter(
                Set.of("Test231"),
                Area.IT, Set.of(Company.BEELINE),
                LocalDateTime.now().minusDays(30));

        var sub = new Subscription("NewSub", filter);

        repo.save(sub);

        Assertions.assertTrue(repo.findOne(new SubByVacancySpecification(vacancy)).isEmpty());
    }

    @Test
    void findByVacancy_onlyInTitle_success() {
        var vacancy = new VacancyDto(
                "sfsgrgsfdrg Test123 dfsgrgsdfgsr",
                "sdrgsdfgrtest231sdfgsrgsdf",
                "Test",
                LocalDateTime.now().minusDays(20), Area.IT, Company.BEELINE);

        var filter = new Filter(
                Set.of("Test123"),
                Area.IT, Set.of(Company.BEELINE),
                LocalDateTime.now().minusDays(30));

        var sub = new Subscription("NewSub", filter);

        repo.save(sub);

        var dbSub = repo.findOne(new SubByVacancySpecification(vacancy)).orElseThrow();

        Assertions.assertEquals(sub, dbSub);
    }

    @Test
    void findByVacancy_onlyInDesc_success() {
        var vacancy = new VacancyDto(
                "sfsgrgsfdrg Tests ggtsdfsgrgsdfgsr",
                "sdrgsdfgrtest123sdfgsrgsdf",
                "Test",
                LocalDateTime.now().minusDays(20), Area.IT, Company.BEELINE);

        var filter = new Filter(
                Set.of("Test123"),
                Area.IT, Set.of(Company.BEELINE),
                LocalDateTime.now().minusDays(30));

        var sub = new Subscription("NewSub", filter);

        repo.save(sub);

        var dbSub = repo.findOne(new SubByVacancySpecification(vacancy)).orElseThrow();

        Assertions.assertEquals(sub, dbSub);
    }

    @Test
    void findByVacancy_severalKeywords_success() {
        var vacancy = new VacancyDto(
                "sfsgrgsfdrg Test123 ggtsdfsgrgsdfgsr",
                "sdrgsdfgrtest231sdfgsrgsdf",
                "Test",
                LocalDateTime.now().minusDays(20), Area.IT, Company.BEELINE);

        var filter = new Filter(
                Set.of("Test123", "Test2314"),
                Area.IT, Set.of(Company.BEELINE),
                LocalDateTime.now().minusDays(30));

        var sub = new Subscription("NewSub", filter);

        repo.save(sub);

        var dbSub = repo.findOne(new SubByVacancySpecification(vacancy)).orElseThrow();

        Assertions.assertEquals(sub, dbSub);
    }

    @Test
    void findByVacancy_severalKeywordsAllContains_success() {
        var vacancy = new VacancyDto(
                "sfsgrgsfdrg Test123 ggtsdfsgrgsdfgsr",
                "sdrgsdfgrtest2314fgsrgsdf",
                "Test",
                LocalDateTime.now().minusDays(20), Area.IT, Company.BEELINE);

        var filter = new Filter(
                Set.of("Test123", "Test2314"),
                Area.IT, Set.of(Company.BEELINE),
                LocalDateTime.now().minusDays(30));

        var sub = new Subscription("NewSub", filter);

        repo.save(sub);

        var dbSub = repo.findOne(new SubByVacancySpecification(vacancy)).orElseThrow();

        Assertions.assertEquals(sub, dbSub);
    }

    @Test
    void findByVacancy_emptyCompany_empty() {
        var vacancy = new VacancyDto(
                "sfsgrgsfdrg Tests ggtsdfsgrgsdfgsr",
                "sdrgsdfgrtest123sdfgsrgsdf",
                "Test",
                LocalDateTime.now().minusDays(20), Area.IT, Company.BEELINE);

        var filter = new Filter(
                Set.of("Test123"),
                Area.IT, Set.of(),
                LocalDateTime.now().minusDays(30));

        var sub = new Subscription("NewSub", filter);

        repo.save(sub);

        Assertions.assertTrue(repo.findOne(new SubByVacancySpecification(vacancy)).isEmpty());
    }
}
