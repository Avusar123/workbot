package com.workbot.workbot.entry.parse;

import com.workbot.workbot.entry.parse.section.util.SectionProvider;
import com.workbot.workbot.entry.parse.util.TestSectionParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestParserConfiguration {
    @Bean
    public TestSectionParser testSectionParser() {
        return new TestSectionParser();
    }

    @Bean
    public SectionProvider provider() {
        return new SectionProvider();
    }
}
