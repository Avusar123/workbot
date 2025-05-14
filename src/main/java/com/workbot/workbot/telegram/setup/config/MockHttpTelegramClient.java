package com.workbot.workbot.telegram.setup.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;

public class MockHttpTelegramClient extends OkHttpTelegramClient {
    private static final Logger log = LoggerFactory.getLogger(MockHttpTelegramClient.class);

    public MockHttpTelegramClient() {
        super("");
    }

    @Override
    public <T extends Serializable, Method extends BotApiMethod<T>> T execute(Method method) throws TelegramApiException {
        log.info("Executed method {}", method.getMethod());

        return null;
    }
}
