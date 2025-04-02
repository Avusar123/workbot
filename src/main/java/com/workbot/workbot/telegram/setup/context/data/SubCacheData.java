package com.workbot.workbot.telegram.setup.context.data;

import com.workbot.workbot.data.model.dto.SubscriptionDto;

public class SubCacheData extends CacheData {
    private SubscriptionDto subscriptionDto;

    public SubCacheData(SubscriptionDto subscriptionDto) {
        this.subscriptionDto = subscriptionDto;
    }

    protected SubCacheData() {}

    public SubscriptionDto getSubscriptionDto() {
        return subscriptionDto;
    }


    public void setSubscriptionDto(SubscriptionDto subscriptionDto) {
        this.subscriptionDto = subscriptionDto;
    }
}
