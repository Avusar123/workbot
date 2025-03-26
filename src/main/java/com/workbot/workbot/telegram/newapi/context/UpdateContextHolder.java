package com.workbot.workbot.telegram.newapi.context;

import com.workbot.workbot.data.model.dto.UserDto;

public class UpdateContextHolder {
    ThreadLocal<UpdateContext> threadLocal = new ThreadLocal<>();

    public UpdateContext getCurrent() {
        return threadLocal.get();
    }

    public long getUserId() {
        return threadLocal.get().getUser().getId();
    }

    public void save(UpdateContext context) {
        threadLocal.set(context);
    }

    public void flush() {
        threadLocal.remove();;
    }
}
