package com.workbot.workbot.logic.service.sub;

import com.workbot.workbot.data.model.dto.SubscriptionDto;
import com.workbot.workbot.data.model.dto.VacancyDto;

import java.util.List;

public interface SubService {
    List<SubscriptionDto> getAllBy(VacancyDto vacancy);

    void add(SubscriptionDto sub);

    void delete(int id);
}
