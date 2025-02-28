package com.workbot.workbot.entry.update.section.util;

import com.workbot.workbot.entry.update.section.SectionParser;
import com.workbot.workbot.model.Area;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public List<SectionParser> getAll(Area area) {
        return data.getOrDefault(area, List.of());
    }
}
