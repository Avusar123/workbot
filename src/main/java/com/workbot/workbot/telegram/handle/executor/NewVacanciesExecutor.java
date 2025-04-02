package com.workbot.workbot.telegram.handle.executor;

import com.workbot.workbot.telegram.handle.handler.NewVacancyHandler;
import com.workbot.workbot.telegram.setup.intent.VacanciesUpdateIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class NewVacanciesExecutor implements HandlerExecutor<VacanciesUpdateIntent> {
    @Autowired
    private NewVacancyHandler newVacancyHandler;

    @Override
    public boolean supports(VacanciesUpdateIntent intent) {
        return !intent.getNewVacancies().isEmpty();
    }

    @Override
    public void execute(VacanciesUpdateIntent intent) throws TelegramApiException {
        for (var vac : intent.getNewVacancies()) {
            newVacancyHandler.process(vac);
        }
    }

    @Override
    public Class<VacanciesUpdateIntent> getIntentType() {
        return VacanciesUpdateIntent.class;
    }
}
