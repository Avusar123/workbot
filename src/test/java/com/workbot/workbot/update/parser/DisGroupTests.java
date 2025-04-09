package com.workbot.workbot.update.parser;

import com.workbot.workbot.logic.json.JsonConfiguration;
import com.workbot.workbot.logic.update.UpdateConfig;
import com.workbot.workbot.logic.update.section.impl.CrokParser;
import com.workbot.workbot.logic.update.section.impl.DIsGroupParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {DIsGroupParser.class, JsonConfiguration.class, UpdateConfig.class })
public class DisGroupTests {
    @Autowired
    private DIsGroupParser dIsGroupParser;

    @Test
    public void parseTest() {
        var result = dIsGroupParser.parse();

        Assertions.assertFalse(result.isEmpty());
    }
}
