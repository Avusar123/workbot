package com.workbot.workbot.update.parser;

import com.workbot.workbot.logic.json.JsonConfiguration;
import com.workbot.workbot.logic.update.UpdateConfig;
import com.workbot.workbot.logic.update.section.impl.BeelineParser;
import com.workbot.workbot.logic.update.section.impl.CrokParser;
import com.workbot.workbot.telegram.TelegramBot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {CrokParser.class, JsonConfiguration.class, UpdateConfig.class })
public class CrokTests {
    @Autowired
    private CrokParser crokParser;

    @Test
    public void parseTest() {
        var result = crokParser.parse();

        Assertions.assertFalse(result.isEmpty());
    }
}
