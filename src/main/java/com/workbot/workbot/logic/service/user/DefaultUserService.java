package com.workbot.workbot.logic.service.user;

import com.workbot.workbot.data.model.UserModel;
import com.workbot.workbot.data.model.dto.SubscriptionDto;
import com.workbot.workbot.data.model.dto.UserDto;
import com.workbot.workbot.data.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserService implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Override
    @Transactional
    public UserDto getOrCreate(long id) {
        var find = userRepo.findById(id);

        UserModel userModel;

        userModel = find.orElseGet(() -> userRepo.save(new UserModel(id)));

        return new UserDto(userModel);
    }

    @Override
    @Transactional
    public Page<SubscriptionDto> getSubs(long id, int maxOnPage, int page) {
        return userRepo
                .findSubsByUser(id, PageRequest.of(page, maxOnPage))
                .map(sub -> new SubscriptionDto(sub, id));
    }
}
