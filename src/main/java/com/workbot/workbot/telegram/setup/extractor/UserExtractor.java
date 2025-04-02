package com.workbot.workbot.telegram.setup.extractor;

import com.workbot.workbot.data.model.dto.UserDto;
import com.workbot.workbot.logic.service.user.UserService;
import com.workbot.workbot.telegram.setup.context.UpdateContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
@Service
public class UserExtractor implements Extractor<UserDto> {
    @Autowired
    private UserService userService;

    @Autowired
    private UpdateContextHolder updateContextHolder;

    @Override
    public UserDto extract(Update update) {
        if (updateContextHolder.contains()) {
            return updateContextHolder.get().user();
        }

        if (update.hasMessage()) {
            return userService.getOrCreate(update.getMessage().getFrom().getId());
        }

        if (update.hasCallbackQuery()) {
            return userService.getOrCreate(update.getCallbackQuery().getFrom().getId());
        }

        throw new UnsupportedOperationException("Cannot parse user from that update");
    }
}
