package com.workbot.workbot.data.model.dto;

import com.workbot.workbot.data.model.Area;
import com.workbot.workbot.data.model.Company;
import com.workbot.workbot.data.model.Filter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class FilterDto {
    int id;

    Set<String> keywords;

    Area area;

    Set<Company> companies;

    LocalDateTime date;

    public FilterDto() {
        companies = new HashSet<>();
        keywords = new HashSet<>();
    }

    public FilterDto(Filter filter) {
        this.id = filter.getId();
        this.keywords = new HashSet<>(filter.getKeywords());
        this.area = filter.getArea();
        this.companies = new HashSet<>(filter.getCompanies());
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        FilterDto filterDto = (FilterDto) o;
        return Objects.equals(keywords, filterDto.keywords) && area == filterDto.area && Objects.equals(companies, filterDto.companies) && Objects.equals(date, filterDto.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keywords, area, companies, date);
    }

    public void setKeywords(Set<String> keywords) {
        this.keywords = keywords;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public void setCompanies(Set<Company> companies) {
        this.companies = companies;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
