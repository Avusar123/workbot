package com.workbot.workbot.telegram.cache.details;

import com.workbot.workbot.data.model.dto.FilterDto;

public class PendingFilterCacheDetails extends MultistepCacheDetails {
    private FilterDto filterDto;

    public PendingFilterCacheDetails(FilterDto filterDto) {
        this.filterDto = filterDto;
    }

    protected PendingFilterCacheDetails() {}

    public FilterDto getFilterDto() {
        return filterDto;
    }

    public void setFilterDto(FilterDto filterDto) {
        this.filterDto = filterDto;
    }

    @Override
    public int getCurrentStep() {
        if (filterDto.getArea() == null) {
            return 0;
        }

        if (filterDto.getCompanies() == null || filterDto.getCompanies().isEmpty()) {
            return 1;
        }

        if (filterDto.getKeywords() == null) {
            return 2;
        }

        if (filterDto.getDate() == null) {
            return 3;
        }

        return 4;
    }

    @Override
    public boolean isCompleted() {
        return getCurrentStep() == 4;
    }
}
