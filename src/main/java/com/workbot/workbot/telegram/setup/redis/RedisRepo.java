package com.workbot.workbot.telegram.setup.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

public abstract class RedisRepo<T> {
    @Autowired
    protected RedisTemplate<String, T> redisTemplate;

    public abstract String postfix();

    public void save(long userId, int messageId, T model) {
        redisTemplate.opsForValue().set(generateKey(userId, messageId), model, Duration.ofHours(1));
    }

    public boolean contains(long userId, int messageId) {
        return redisTemplate.hasKey(generateKey(userId, messageId));
    }

    public T get(long userId, int messageId) {
        var key = generateKey(userId, messageId);

        return redisTemplate.opsForValue().get(key);
    }

    protected String generateKey(long userId, int messageId) {
        return userId + ":" + messageId + ":" + postfix();
    }
}
