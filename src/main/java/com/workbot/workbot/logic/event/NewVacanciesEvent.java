package com.workbot.workbot.logic.event;

import com.workbot.workbot.data.model.dto.VacancyDto;
import org.springframework.context.ApplicationEvent;

import java.util.Set;

public class NewVacanciesEvent extends ApplicationEvent {

    private final Set<VacancyDto> vacancies;

    public NewVacanciesEvent(Object source, Set<VacancyDto> vacancies) {
        super(source);
        this.vacancies = vacancies;
    }

    public Set<VacancyDto> getVacancies() {
        return vacancies;
    }
}
