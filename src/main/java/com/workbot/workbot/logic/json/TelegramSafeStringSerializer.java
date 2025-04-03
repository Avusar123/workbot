package com.workbot.workbot.logic.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.workbot.workbot.data.model.dto.util.TelegramSafeString;

import java.io.IOException;

public class TelegramSafeStringSerializer extends JsonSerializer<TelegramSafeString> {
    @Override
    public void serialize(TelegramSafeString telegramSafeString, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(telegramSafeString.getUnsafe());
    }
}
