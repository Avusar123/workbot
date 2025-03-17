package com.workbot.workbot.logic.service.user;

import com.workbot.workbot.data.model.UserModel;
import com.workbot.workbot.data.model.dto.SubscriptionDto;
import com.workbot.workbot.data.model.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto getOrCreate(long id);

    List<SubscriptionDto> getSubs(long userId, int maxOnPage, int page);
}
