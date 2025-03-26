package com.workbot.workbot.telegram.newapi.redis;

import com.workbot.workbot.telegram.newapi.context.pagination.PaginationContext;
import org.springframework.stereotype.Service;

@Service
public class PaginationRepo extends CacheRepo<PaginationContext> {
    @Override
    public String postfix() {
        return "pagination";
    }
}
