package com.workbot.workbot.data.model;

import com.workbot.workbot.data.model.dto.VacancyDto;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Vacancy {
    @Id
    @GeneratedValue
    UUID id;

    String title;

    String description;

    String link;

    LocalDateTime added;

    @Enumerated(EnumType.STRING)
    Area area;

    @Enumerated(EnumType.STRING)
    Company company;

    protected Vacancy() {}

    public Vacancy(String title,
                   String description,
                   String link,
                   LocalDateTime added,
                   Area area,
                   Company company) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.added = added;
        this.area = area;
        this.company = company;
    }

    public Vacancy(VacancyDto dto) {
        this.title = dto.getTitle();
        this.description = dto.getDescription();
        this.link = dto.getLink();
        this.added = dto.getAdded();
        this.area = dto.getArea();
        this.company = dto.getCompany();

    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public LocalDateTime getAdded() {
        return added;
    }

    public void setAdded(LocalDateTime added) {
        this.added = added;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
