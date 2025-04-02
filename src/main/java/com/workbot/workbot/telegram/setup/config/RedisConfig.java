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
    public RedisTemplate<String, CacheData> cacheDataRedisTemplate(RedisConnectionFactory connectionFactory) {
        return templateFromClass(connectionFactory, CacheData.class);
    }

    @Bean
    public RedisTemplate<String, PaginationContext> paginationContextRedisTemplate(RedisConnectionFactory connectionFactory) {
        return templateFromClass(connectionFactory, PaginationContext.class);
    }

    @Bean
    public RedisTemplate<String , Integer> ChatDelegatedMessagesRepo(RedisConnectionFactory connectionFactory) {
        var redisTemplate = new RedisTemplate<String, Integer>();

        redisTemplate.setConnectionFactory(connectionFactory);

        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

    private <T> RedisTemplate<String, T> templateFromClass(RedisConnectionFactory connectionFactory, Class<T> valueObject) {
        RedisTemplate<String, T> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        var objectMapper = new ObjectMapper();

        objectMapper.registerModule(new JavaTimeModule());

        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(objectMapper, valueObject));
        template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(objectMapper, valueObject));

        template.afterPropertiesSet();

        return template;
    }
}