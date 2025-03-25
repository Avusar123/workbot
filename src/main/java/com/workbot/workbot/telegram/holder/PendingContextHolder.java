package com.workbot.workbot.telegram.holder;

import com.workbot.workbot.telegram.cache.details.CacheDetails;
import com.workbot.workbot.telegram.cache.model.PendingModel;
import org.springframework.stereotype.Service;

@Service
public class PendingContextHolder {
    ThreadLocal<PendingModel> threadLocal = new ThreadLocal<>();

    public PendingModel getCurrent() {
        return threadLocal.get();
    }

    public String getSender() {
        return threadLocal.get().getSender();
    }

    public Class<? extends CacheDetails> getDetailsClass() {
        return threadLocal.get().getActionDetails().getClass();
    }

    public boolean hasDetails() {
        return threadLocal.get().hasDetails();
    }

    public void save(PendingModel model) {
        threadLocal.set(model);
    }

    public boolean has() {
        return threadLocal.get() != null;
    }

    public void flush() {
        threadLocal.remove();;
    }
}
