package com.workbot.workbot.logic.handler;

import com.workbot.workbot.logic.event.NewVacanciesEvent;
import org.springframework.context.event.EventListener;

public interface SubTriggerHandler {
    @EventListener
    void handleNewVacancies(NewVacanciesEvent event);
}
