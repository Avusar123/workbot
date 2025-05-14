package com.workbot.workbot.telegram.setup.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

@Configuration
public class TelegramConfiguration {

    @Bean
    @Profile("!test")
    public TelegramBotsLongPollingApplication telegramBotsLongPollingApplication() {
        return new TelegramBotsLongPollingApplication();
    }

    @Bean
    @Profile("!test")
    public OkHttpTelegramClient httpTelegramClient(@Value("${tg.token}") String token) {
        return new OkHttpTelegramClient(token);
    }

    @Bean
    @Profile("test")
    public OkHttpTelegramClient mockTelegramClient() {
        return new MockHttpTelegramClient();
    }
}
