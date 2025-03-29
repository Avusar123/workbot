package com.workbot.workbot.telegram.newapi.setup.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public abstract class RedisRepo<T> {
    @Autowired
    protected RedisTemplate<String, T> redisTemplate;

    public abstract String postfix();

    public void save(long userId, int messageId, T model) {
        redisTemplate.opsForValue().set(generateKey(userId, messageId), model);
    }

    public boolean contains(long userId, int messageId) {
        return redisTemplate.hasKey(generateKey(userId, messageId));
    }

    public T get(long userId, int messageId) {
        var key = generateKey(userId, messageId);

        return redisTemplate.opsForValue().get(key);
    }

    private String generateKey(long userId, int messageId) {
        return userId + ":" + messageId + ":" + postfix();
    }
}
