package com.workbot.workbot.data.model.dto;

import com.workbot.workbot.data.model.Area;
import com.workbot.workbot.data.model.Company;
import com.workbot.workbot.data.model.Vacancy;
import com.workbot.workbot.data.model.dto.util.TelegramSafeString;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class VacancyDto {
    final UUID id;

    final TelegramSafeString title;

    final TelegramSafeString description;

    final String link;

    final LocalDateTime added;

    Area area;

    Company company;

    public VacancyDto(String title, String description, String link, LocalDateTime added, Area area, Company company) {
        this.title = new TelegramSafeString(title);
        this.description = new TelegramSafeString(description.toLowerCase());
        this.link = link;
        this.added = added;
        this.area = area;
        this.company = company;
        this.id = null;
    }

    public VacancyDto(Vacancy vacancy) {
        this.id = vacancy.getId();
        this.title = new TelegramSafeString(vacancy.getTitle());
        this.description = new TelegramSafeString(vacancy.getDescription().toLowerCase());
        this.company = vacancy.getCompany();
        this.link = vacancy.getLink();
        this.added = vacancy.getAdded();
        this.area = vacancy.getArea();
    }

    public UUID getId() {
        return id;
    }

    public TelegramSafeString getTitle() {
        return title;
    }

    public TelegramSafeString getDescription() {
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        VacancyDto that = (VacancyDto) o;
        return Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(link, that.link) && area == that.area && company == that.company;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, link, area, company);
    }
}
