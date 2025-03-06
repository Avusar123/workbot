package com.workbot.workbot.data.model.dto;

import com.workbot.workbot.data.model.Area;
import com.workbot.workbot.data.model.Company;
import com.workbot.workbot.data.model.Filter;

import java.time.LocalDateTime;
import java.util.Set;

public class FilterDto {
    final int id;

    final Set<String> keywords;

    final Area area;

    final Set<Company> companies;

    final LocalDateTime date;

    public FilterDto(Set<String> keywords, Area area, Set<Company> companies, LocalDateTime date) {
        this.id = -1;
        this.keywords = keywords;
        this.area = area;
        this.companies = companies;
        this.date = date;
    }

    public FilterDto(Filter filter) {
        this.id = filter.getId();
        this.keywords = filter.getKeywords();
        this.area = filter.getArea();
        this.companies = filter.getCompanies();
        this.date = filter.getDate();
    }

    public int getId() {
        return id;
    }

    public Set<String> getKeywords() {
        return keywords;
    }

    public Area getArea() {
        return area;
    }

    public Set<Company> getCompanies() {
        return companies;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
