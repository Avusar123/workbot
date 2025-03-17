package com.workbot.workbot.telegram.util;

import com.workbot.workbot.data.model.dto.UserDto;
import com.workbot.workbot.logic.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserContextHolder {

    ThreadLocal<UserDto> threadLocal = new ThreadLocal<>();

    public UserDto getCurrent() {
        return threadLocal.get();
    }

    public long getId() {
        return threadLocal.get().getId();
    }

    public void save(UserDto userDto) {
        threadLocal.set(userDto);
    }

    public void flush() {
        threadLocal.remove();;
    }
}
