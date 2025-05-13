package com.workbot.workbot.data.model;

import com.workbot.workbot.data.model.dto.FilterDto;
import com.workbot.workbot.data.model.dto.util.TelegramSafeString;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Filter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ElementCollection
    @CollectionTable(
            name = "filter_keywords",
            joinColumns = @JoinColumn(name = "filter_id")
    )
    @Column(name = "keyword")
    Set<String> keywords;

    @Enumerated(EnumType.STRING)
    Area area;

    @ElementCollection
    @CollectionTable(
            name = "filter_companies",
            joinColumns = @JoinColumn(name = "filter_id")
    )
    @Column(name = "company")
    @Enumerated(EnumType.STRING)
    Set<Company> companies;

    LocalDateTime date;

    public Filter(Set<String> keywords, Area area, Set<Company> companies, LocalDateTime date) {
        this.keywords = keywords;
        this.area = area;
        this.companies = companies;
        this.date = date;
    }

    public Filter(FilterDto filterDto) {
        this(filterDto
                .getKeywords()
                .stream()
                .map(TelegramSafeString::getUnsafe)
                .collect(Collectors.toSet()), filterDto.getArea(), filterDto.getCompanies(), filterDto.getDate());
    }

    protected Filter() {

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
