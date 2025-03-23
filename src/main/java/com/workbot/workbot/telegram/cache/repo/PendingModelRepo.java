package com.workbot.workbot.telegram.cache.repo;

import com.workbot.workbot.telegram.cache.pending.PendingModel;
import org.springframework.stereotype.Service;

@Service
public class PendingModelRepo extends CacheRepo<PendingModel> {
    @Override
    public String postfix() {
        return "pending";
    }
}
