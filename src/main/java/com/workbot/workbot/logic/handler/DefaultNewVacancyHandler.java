package com.workbot.workbot.logic.handler;

import com.workbot.workbot.logic.event.NewVacanciesEvent;
import com.workbot.workbot.logic.event.SubTriggeredEvent;
import com.workbot.workbot.logic.service.sub.SubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class DefaultNewVacancyHandler implements NewVacancyHandler {
    @Autowired
    private SubService subService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Override
    public void handleNewVacancies(NewVacanciesEvent event) {
        for (var vac : event.getVacancies()) {
            var subs =  subService.getAllBy(vac);

            publisher.publishEvent(new SubTriggeredEvent(this, subs));
        }
    }
}
