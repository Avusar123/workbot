package com.workbot.workbot.telegram.setup.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.workbot.workbot.telegram.setup.context.data.CacheData;
import com.workbot.workbot.telegram.setup.redis.PaginationContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, CacheData> cacheDataRedisTemplate(
            RedisConnectionFactory connectionFactory,
            ObjectMapper mapper) {
        return templateFromClass(connectionFactory, CacheData.class, mapper);
    }

    @Bean
    public RedisTemplate<String, PaginationContext> paginationContextRedisTemplate(
            RedisConnectionFactory connectionFactory,
            ObjectMapper mapper) {
        return templateFromClass(connectionFactory, PaginationContext.class, mapper);
    }

    @Bean
    public RedisTemplate<String , Integer> ChatDelegatedMessagesRepo(RedisConnectionFactory connectionFactory) {
        var redisTemplate = new RedisTemplate<String, Integer>();

        redisTemplate.setConnectionFactory(connectionFactory);

        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

    private <T> RedisTemplate<String, T> templateFromClass(RedisConnectionFactory connectionFactory,
                                                           Class<T> valueObject,
                                                           ObjectMapper mapper) {
        RedisTemplate<String, T> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(mapper, valueObject));
        template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(mapper, valueObject));

        template.afterPropertiesSet();

        return template;
    }
}