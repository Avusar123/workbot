package com.workbot.workbot.logic.service.sub;

import com.workbot.workbot.data.model.Filter;
import com.workbot.workbot.data.model.Subscription;
import com.workbot.workbot.data.model.dto.SubscriptionDto;
import com.workbot.workbot.data.model.dto.VacancyDto;
import com.workbot.workbot.data.repo.SubRepo;
import com.workbot.workbot.data.repo.criteria.SubByVacancySpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultSubService implements SubService {
    @Autowired
    private SubRepo repo;

    @Override
    public List<SubscriptionDto> getAllBy(VacancyDto vacancy) {
        return repo.findAll(new SubByVacancySpecification(vacancy)).stream().map(
                sub -> new SubscriptionDto(sub, sub.getUser().getId())
        ).toList();
    }

    @Override
    public void add(SubscriptionDto sub) {
        repo.save(new Subscription(sub.getTitle(), new Filter(sub.getFilter())));
    }

    @Override
    public void delete(int id) {
        repo.deleteById(id);
    }
}
