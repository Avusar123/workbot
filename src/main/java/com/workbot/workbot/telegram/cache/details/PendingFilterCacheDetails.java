package com.workbot.workbot.telegram.cache.details;

import com.workbot.workbot.data.model.dto.FilterDto;

public class PendingFilterCacheDetails extends CacheDetails {
    private FilterDto filterDto;

    public PendingFilterCacheDetails(FilterDto filterDto) {
        this.filterDto = filterDto;
    }

    public FilterDto getFilterDto() {
        return filterDto;
    }

    public void setFilterDto(FilterDto filterDto) {
        this.filterDto = filterDto;
    }
}
