package com.workbot.workbot.logic.event;

import com.workbot.workbot.data.model.Vacancy;
import org.springframework.context.ApplicationEvent;

import java.util.Set;

public class NewVacanciesEvent extends ApplicationEvent {

    private final Set<Vacancy> vacancies;

    public NewVacanciesEvent(Object source, Set<Vacancy> vacancies) {
        super(source);
        this.vacancies = vacancies;
    }

    public Set<Vacancy> getVacancies() {
        return vacancies;
    }
}
