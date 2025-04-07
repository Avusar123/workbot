package com.workbot.workbot.logic.update.section.util;

import com.workbot.workbot.logic.update.section.SectionParser;

public class ParserException extends RuntimeException {

    public ParserException(SectionParser parser, Throwable cause) {
        super("Exception while using parser %s with company %s with area %s: %s".formatted(
                parser.getClass().toString(),
                parser.getCompany().toString(),
                parser.getArea().toString(),
                cause.toString()
        ));
    }
}
