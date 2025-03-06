package com.workbot.workbot.data.repo;

import com.workbot.workbot.data.model.Area;
import com.workbot.workbot.data.model.Company;
import com.workbot.workbot.data.model.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface VacancyRepo extends JpaRepository<Vacancy, UUID>,
        JpaSpecificationExecutor<Vacancy> {
    List<Vacancy> findAllByAreaAndCompany(Area area, Company company);
}
