package com.workbot.workbot.telegram.config;

import com.workbot.workbot.telegram.cache.model.PaginationModel;
import com.workbot.workbot.telegram.cache.model.PendingModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, PaginationModel> paginationModelTemplate(RedisConnectionFactory connectionFactory) {
        return templateFromClass(connectionFactory, PaginationModel.class);
    }

    @Bean
    public RedisTemplate<String, PendingModel> pendingModelTemplate(RedisConnectionFactory connectionFactory) {
        return templateFromClass(connectionFactory, PendingModel.class);
    }

    private <T> RedisTemplate<String, T> templateFromClass(RedisConnectionFactory connectionFactory, Class<T> valueObject) {
        RedisTemplate<String, T> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(valueObject));
        template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(valueObject));

        template.afterPropertiesSet();

        return template;
    }
}