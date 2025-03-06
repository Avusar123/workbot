package com.workbot.workbot.data.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Filter {
    @Id
    @GeneratedValue
    int id;

    @ElementCollection
    Set<String> keywords;

    @Enumerated(EnumType.STRING)
    Area area;

    @ElementCollection
    Set<Company> companies;

    LocalDateTime date;

    public Filter(Set<String> keywords, Area area, Set<Company> companies, LocalDateTime date) {
        this.keywords = keywords;
        this.area = area;
        this.companies = companies;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(Set<String> keywords) {
        this.keywords = keywords;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Set<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(Set<Company> companies) {
        this.companies = companies;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
