package com.workbot.workbot.telegram.setup.context.data;

import com.workbot.workbot.data.model.dto.FilterDto;
import com.workbot.workbot.telegram.handle.handler.filter.FilterState;
import com.workbot.workbot.telegram.setup.intent.type.HandlerType;

public class FilterCacheData extends CacheData {
    private FilterDto filterDto;

    private HandlerType handler;

    private FilterState filterState;

    public FilterDto getFilterDto() {
        return filterDto;
    }

    public void setFilterDto(FilterDto filterDto) {
        this.filterDto = filterDto;
    }

    public HandlerType getHandler() {
        return handler;
    }

    public void setHandler(HandlerType handler) {
        this.handler = handler;
    }

    public FilterState getFilterState() {
        return filterState;
    }

    public void setFilterState(FilterState filterState) {
        this.filterState = filterState;
    }
}
