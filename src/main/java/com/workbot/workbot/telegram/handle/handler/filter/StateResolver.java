package com.workbot.workbot.telegram.handle.handler.filter;

import com.workbot.workbot.telegram.handle.handler.filter.state.AreaFilterStateHandler;
import com.workbot.workbot.telegram.handle.handler.filter.state.CompaniesFilterStateHandler;
import com.workbot.workbot.telegram.handle.handler.filter.state.FilterStateHandler;
import com.workbot.workbot.telegram.handle.handler.filter.state.KeywordsFilterStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class StateResolver {
    @Autowired
    private ApplicationContext context;

    public FilterStateHandler resolve(FilterState filterState) {
        return switch (filterState) {
            case AREA -> context.getBean(AreaFilterStateHandler.class);
            case COMPANIES -> context.getBean(CompaniesFilterStateHandler.class);
            case KEYWORDS -> context.getBean(KeywordsFilterStateHandler.class);
        };
    }
}
