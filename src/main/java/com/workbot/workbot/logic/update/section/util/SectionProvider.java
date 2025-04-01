package com.workbot.workbot.logic.update.section.util;

import com.workbot.workbot.data.model.Area;
import com.workbot.workbot.data.model.Company;
import com.workbot.workbot.logic.update.section.SectionParser;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SectionProvider {

    @Autowired
    ApplicationContext context;

    Map<Area, List<SectionParser>> data = new HashMap<>();

    @PostConstruct
    public void parseCompanies() {
        var parsers = context.getBeansOfType(SectionParser.class).values();

        data = parsers.stream().collect(Collectors.groupingBy(SectionParser::getArea));
    }

    public List<SectionParser> getAll() {
        return data.values().stream().flatMap(Collection::stream).toList();
    }

    public Set<Area> getAllAreas() {
        return data.keySet();
    }

    public List<SectionParser> getAll(Area area) {
        return data.getOrDefault(area, List.of());
    }

    public List<Company> getAllCompanies(Area area) {
        return getAll(area).stream().map(SectionParser::getCompany).toList();
    }
}
