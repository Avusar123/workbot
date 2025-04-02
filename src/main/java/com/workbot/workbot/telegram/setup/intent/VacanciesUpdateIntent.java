package com.workbot.workbot.telegram.setup.intent;

import com.workbot.workbot.data.model.dto.VacancyDto;

import java.util.Set;

public class VacanciesUpdateIntent extends UpdateIntent {
    private final Set<VacancyDto> newVacancies;

    private final Set<VacancyDto> deletedVacancies;

    public VacanciesUpdateIntent(Set<VacancyDto> newVacancies, Set<VacancyDto> deletedVacancies) {
        this.newVacancies = newVacancies;
        this.deletedVacancies = deletedVacancies;
    }

    public Set<VacancyDto> getNewVacancies() {
        return newVacancies;
    }

    public Set<VacancyDto> getDeletedVacancies() {
        return deletedVacancies;
    }
}
