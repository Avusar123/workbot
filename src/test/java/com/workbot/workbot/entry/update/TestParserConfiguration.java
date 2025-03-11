package com.workbot.workbot.entry.update;

import com.workbot.workbot.entry.update.util.TestSectionParser;
import com.workbot.workbot.logic.service.update.section.util.SectionProvider;
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
