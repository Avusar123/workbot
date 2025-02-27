package com.workbot.workbot.entry.parse.section;

import com.workbot.workbot.entry.parse.ParseEntry;
import com.workbot.workbot.model.Vacancy;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SectionParseEntry implements ParseEntry {
    @Override
    public Set<Vacancy> parse() {
        return Set.of();
    }
}
