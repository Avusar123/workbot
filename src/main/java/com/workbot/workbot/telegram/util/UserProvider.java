package com.workbot.workbot.telegram.util;

import com.workbot.workbot.data.model.dto.UserDto;
import com.workbot.workbot.logic.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class UserProvider {
    @Autowired
    private UserService userService;

    public UserDto get(Update update) {
        if (update.hasMessage()) {
            return userService.getOrCreate(update.getMessage().getFrom().getId());
        }

        if (update.hasCallbackQuery()) {
            return userService.getOrCreate(update.getCallbackQuery().getFrom().getId());
        }

        throw new UnsupportedOperationException("User Id could not be parsed");
    }
}
