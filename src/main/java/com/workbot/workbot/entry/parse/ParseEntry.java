package com.workbot.workbot.entry.parse;

import com.workbot.workbot.model.Vacancy;

import java.util.Set;

public interface ParseEntry {
    Set<Vacancy> parse();
}
