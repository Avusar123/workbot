package com.workbot.workbot.telegram.cache.repo;

import com.workbot.workbot.telegram.cache.pagination.PaginationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public abstract class CacheRepo<T> {
    @Autowired
    private RedisTemplate<String, T> redisTemplate;

    public abstract String postfix();

    public void save(long userId, int messageId, T model) {
        redisTemplate.opsForValue().set(generateKey(userId, messageId), model);
    }

    public T get(long userId, int messageId) {
        var key = generateKey(userId, messageId);

        return redisTemplate.opsForValue().get(key);
    }

    private String generateKey(long userId, int messageId) {
        return userId + ":" + messageId + ":" + postfix();
    }
}
