package com.workbot.workbot.logic.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.workbot.workbot.data.model.dto.util.TelegramSafeString;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonConfiguration {
    @Bean
    public ObjectMapper objectMapper() {
        var objectMapper = new ObjectMapper();

        objectMapper.registerModule(new JavaTimeModule());

        SimpleModule simpleModule = new SimpleModule();

        simpleModule.addSerializer(TelegramSafeString.class, new TelegramSafeStringSerializer());

        simpleModule.addDeserializer(TelegramSafeString.class, new TelegramSafeStringDeserializer());

        objectMapper.registerModule(simpleModule);

        return objectMapper;
    }
}
