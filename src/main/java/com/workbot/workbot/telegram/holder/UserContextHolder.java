package com.workbot.workbot.telegram.holder;

import com.workbot.workbot.data.model.dto.UserDto;
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
