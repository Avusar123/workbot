package com.workbot.workbot.data.model.dto;

import com.workbot.workbot.data.model.Subscription;

public class SubscriptionDto {
    final int id;

    final String title;

    final FilterDto filter;


    public SubscriptionDto(FilterDto filter, String title) {
        this.filter = filter;
        this.title = title;
        this.id = -1;
    }

    public SubscriptionDto(Subscription subscription) {
        this.id = subscription.getId();
        this.filter = new FilterDto(subscription.getFilter());
        this.title = subscription.getTitle();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public FilterDto getFilter() {
        return filter;
    }
}
