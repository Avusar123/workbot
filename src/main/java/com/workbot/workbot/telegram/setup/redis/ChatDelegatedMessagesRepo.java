package com.workbot.workbot.telegram.setup.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ChatDelegatedMessagesRepo {
    @Autowired
    protected RedisTemplate<String, Integer> redisTemplate;

    public void save(long userId, int messageId) {
        redisTemplate.opsForValue().set(generateKey(userId), messageId);
    }

    public String generateKey(long userId) {
        return userId + ":delayed";
    }

    public boolean contains(long userId) {
        return redisTemplate.hasKey(generateKey(userId));
    }

    public void flush(long userId) {
        redisTemplate.delete(generateKey(userId));
    }

    public int get(long userId) {
        return Objects.requireNonNull(redisTemplate.opsForValue().get(generateKey(userId)));
    }
}
