package com.workbot.workbot.repo;

import com.workbot.workbot.model.Area;
import com.workbot.workbot.model.Company;
import com.workbot.workbot.model.Filter;
import com.workbot.workbot.model.Vacancy;
import com.workbot.workbot.repo.criteria.VacancyByFilterSpecification;
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
