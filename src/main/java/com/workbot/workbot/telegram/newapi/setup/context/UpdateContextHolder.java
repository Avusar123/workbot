package com.workbot.workbot.telegram.newapi.setup.context;

import org.springframework.stereotype.Service;

@Service
public class UpdateContextHolder {
    private final ThreadLocal<UpdateContext> threadLocal = new ThreadLocal<>();

    public UpdateContext get() {
        if (!contains()) {
            throw new NullPointerException("There is no update context");
        }

        return threadLocal.get();
    }

    public void flush() {
        threadLocal.remove();
    }

    public void set(UpdateContext context) {
        threadLocal.set(context);
    }

    public boolean contains() {
        return threadLocal.get() != null;
    }
}
