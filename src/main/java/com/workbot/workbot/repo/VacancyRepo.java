package com.workbot.workbot.repo;

import com.workbot.workbot.model.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface VacancyRepo extends JpaRepository<Vacancy, UUID> {
}
