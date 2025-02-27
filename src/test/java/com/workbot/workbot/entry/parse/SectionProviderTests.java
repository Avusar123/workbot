package com.workbot.workbot.entry.parse;

import com.workbot.workbot.entry.parse.section.util.SectionProvider;
import com.workbot.workbot.entry.parse.util.TestSectionParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(classes = TestParserConfiguration.class)
@ExtendWith(SpringExtension.class)
public class SectionProviderTests {

    @Autowired
    private TestSectionParser parser;

    @Autowired
    private SectionProvider provider;

    @Test
    void test() {
        var parsers = provider.getAll();

        Assertions.assertEquals(parsers.getFirst(), parser);
    }
}
