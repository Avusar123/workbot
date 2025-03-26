package com.workbot.workbot.telegram.newapi.redis;

import com.workbot.workbot.telegram.newapi.context.model.ModelContext;
import org.springframework.stereotype.Service;

@Service
public class ModelRepo extends CacheRepo<ModelContext> {
    public void saveForChat(long userId, ModelContext model) {
        redisTemplate.opsForValue().set(generateKeyForChat(userId), model);
    }

    public ModelContext getForChat(long userId) {
        return redisTemplate.opsForValue().get(generateKeyForChat(userId));
    }

    public void flushForChat(long userId) {
        redisTemplate.delete(generateKeyForChat(userId));
    }

    public boolean hasForChat(long userId) {
        return redisTemplate.hasKey(generateKeyForChat(userId));
    }

    private String generateKeyForChat(long userId) {
        return userId + ":" + postfix();
    }

    @Override
    public String postfix() {
        return "model";
    }
}
