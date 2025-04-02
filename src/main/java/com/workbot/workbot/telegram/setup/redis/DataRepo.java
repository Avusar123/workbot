package com.workbot.workbot.telegram.setup.redis;

import com.workbot.workbot.telegram.setup.context.data.CacheData;
import org.springframework.stereotype.Service;

@Service
public class DataRepo extends RedisRepo<CacheData> {

    @Override
    public String postfix() {
        return "data";
    }
}
