package com.workbot.workbot.telegram.newapi.processor.extractor;

import com.workbot.workbot.data.model.dto.UserDto;
import com.workbot.workbot.logic.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class UserExtractor implements Extractor<UserDto> {
    @Autowired
    private UserService userService;

    @Override
    public UserDto extract(Update update) {
        long userId;

        if (update.hasMessage()) {
            userId = update.getMessage().getFrom().getId();
        } else if (update.hasCallbackQuery()) {
            userId = update.getCallbackQuery().getFrom().getId();
        } else {
            throw new UnsupportedOperationException("User cannot be parsed");
        }

        return userService.getOrCreate(userId);
    }

    @Override
    public boolean has(Update update) {
        return true;
    }
}
