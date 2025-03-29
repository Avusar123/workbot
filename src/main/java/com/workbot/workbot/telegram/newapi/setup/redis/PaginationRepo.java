package com.workbot.workbot.telegram.newapi.setup.redis;

import org.springframework.stereotype.Service;

@Service
public class PaginationRepo extends RedisRepo<PaginationContext> {
    @Override
    public String postfix() {
        return "pagination";
    }
}
