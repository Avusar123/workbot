package com.workbot.workbot.telegram.setup.context.data;

import com.workbot.workbot.data.model.dto.FilterDto;

public class SearchFilterCacheData extends CacheData {
    private FilterDto filterDto;

    public SearchFilterCacheData(FilterDto filterDto) {
        this.filterDto = filterDto;
    }

    protected SearchFilterCacheData() {}

    public FilterDto getFilterDto() {
        return filterDto;
    }
}
