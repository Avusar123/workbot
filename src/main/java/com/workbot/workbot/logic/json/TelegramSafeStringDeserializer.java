package com.workbot.workbot.logic.json;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.workbot.workbot.data.model.dto.util.TelegramSafeString;

import java.io.IOException;

public class TelegramSafeStringDeserializer extends JsonDeserializer<TelegramSafeString> {
    @Override
    public TelegramSafeString deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        return new TelegramSafeString(jsonParser.getText());
    }
}
