package com.workbot.workbot.data.model.dto;

import com.workbot.workbot.data.model.Area;
import com.workbot.workbot.data.model.Company;
import com.workbot.workbot.data.model.Vacancy;

import java.time.LocalDateTime;
import java.util.UUID;

public class VacancyDto {
    final UUID id;

    final String title;

    final String description;

    final String link;

    final LocalDateTime added;

    Area area;

    Company company;

    public VacancyDto(String title, String description, String link, LocalDateTime added, Area area, Company company) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.added = added;
        this.area = area;
        this.company = company;
        this.id = null;
    }

    public VacancyDto(Vacancy vacancy) {
        this.id = vacancy.getId();
        this.title = vacancy.getTitle();
        this.description = vacancy.getDescription();
        this.link = vacancy.getLink();
        this.added = vacancy.getAdded();
        this.area = vacancy.getArea();
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public LocalDateTime getAdded() {
        return added;
    }

    public Area getArea() {
        return area;
    }

    public Company getCompany() {
        return company;
    }
}
