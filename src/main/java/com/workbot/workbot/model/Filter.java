package com.workbot.workbot.model;

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
}
