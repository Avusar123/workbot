package com.workbot.workbot.logic.service.sub;

import com.workbot.workbot.data.model.dto.SubscriptionDto;
import com.workbot.workbot.data.model.dto.VacancyDto;
import com.workbot.workbot.data.repo.SubRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultSubService implements SubService {
    @Autowired
    private SubRepo repo;

    @Override
    public List<SubscriptionDto> getAllBy(VacancyDto vacancy) {
        return List.of();
    }

    @Override
    public void add(SubscriptionDto sub) {
    }

    @Override
    public void delete(int id) {

    }
}
