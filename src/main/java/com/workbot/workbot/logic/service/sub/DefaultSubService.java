package com.workbot.workbot.logic.service.sub;

import com.workbot.workbot.data.model.Filter;
import com.workbot.workbot.data.model.Subscription;
import com.workbot.workbot.data.model.UserModel;
import com.workbot.workbot.data.model.dto.SubscriptionDto;
import com.workbot.workbot.data.model.dto.VacancyDto;
import com.workbot.workbot.data.repo.SubRepo;
import com.workbot.workbot.data.repo.UserRepo;
import com.workbot.workbot.data.repo.criteria.SubByVacancySpecification;
import com.workbot.workbot.logic.service.user.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultSubService implements SubService {
    @Autowired
    private SubRepo repo;

    @Autowired
    private UserRepo userRepo;

    @Override
    @Transactional
    public List<SubscriptionDto> getAllBy(VacancyDto vacancy) {
        return repo.findAll(new SubByVacancySpecification(vacancy)).stream().map(
                sub -> new SubscriptionDto(sub, sub.getUser().getId())
        ).toList();
    }

    @Override
    @Transactional
    public int add(SubscriptionDto sub) {
        if (sub.getUserId() < 0) {
            throw new IllegalArgumentException("UserId must be set!");
        }

        var dbSub = new Subscription(sub.getTitle(), new Filter(sub.getFilter()));

        var user = userRepo.findById(sub.getUserId()).orElseThrow();

        dbSub.setUser(user);

        return repo.save(dbSub).getId();
    }

    @Override
    @Transactional
    public SubscriptionDto get(int id, long userId) {
        var sub = repo.findById(id).orElseThrow();

        return new SubscriptionDto(sub, userId);
    }

    @Override
    public void delete(int id) {
        repo.deleteById(id);
    }
}
