package com.workbot.workbot.telegram.setup.redis;

import com.workbot.workbot.telegram.setup.context.data.CacheData;
import org.springframework.stereotype.Service;

@Service
public class DataRepo extends RedisRepo<CacheData> {

    public void flush(long userId, int messageId) {
        redisTemplate.delete(generateKey(userId, messageId));
    }

    @Override
    public String postfix() {
        return "data";
    }
}
