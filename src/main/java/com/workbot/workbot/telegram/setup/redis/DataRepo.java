package com.workbot.workbot.telegram.setup.redis;

import com.workbot.workbot.telegram.setup.context.data.CacheData;
import org.springframework.stereotype.Service;

@Service
public class DataRepo extends RedisRepo<CacheData> {
    public void saveForChat(long userId, CacheData data) {
        redisTemplate.opsForValue().set(generateKeyForChat(userId), data);
    }

    public CacheData getForChat(long userId) {
        return redisTemplate.opsForValue().get(generateKeyForChat(userId));
    }

    public void flushForChat(long userId) {
        redisTemplate.delete(generateKeyForChat(userId));
    }

    public boolean containsForChat(long userId) {
        return redisTemplate.hasKey(generateKeyForChat(userId));
    }

    private String generateKeyForChat(long userId) {
        return userId + ":" + postfix();
    }

    @Override
    public String postfix() {
        return "data";
    }
}
