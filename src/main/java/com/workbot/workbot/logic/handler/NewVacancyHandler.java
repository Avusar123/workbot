package com.workbot.workbot.logic.handler;

import com.workbot.workbot.logic.event.NewVacanciesEvent;

public interface NewVacancyHandler {
     void handleNewVacancies(NewVacanciesEvent event);
}
