package com.workbot.workbot.telegram.pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaginationRepo {
    @Autowired
    private RedisTemplate<String, PaginationModel> redisTemplate;

    public void save(long userId, int messageId, PaginationModel model) {
        redisTemplate.opsForValue().set(generateKey(userId, messageId), model);
    }

    public PaginationModel get(long userId, int messageId) {
        var key = generateKey(userId, messageId);

        return redisTemplate.opsForValue().get(key);
    }

    private String generateKey(long userId, int messageId) {
        return userId + ":" + messageId + ":" + "pagination";
    }
}
