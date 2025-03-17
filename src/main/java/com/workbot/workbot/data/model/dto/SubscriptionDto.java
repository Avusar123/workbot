package com.workbot.workbot.data.model.dto;

import com.workbot.workbot.data.model.Subscription;

import java.util.Objects;

public class SubscriptionDto {
    final int id;

    final String title;

    final FilterDto filter;

    final long userId;


    public SubscriptionDto(FilterDto filter, String title, long userId) {
        this.filter = filter;
        this.title = title;
        this.userId = userId;
        this.id = -1;
    }

    public SubscriptionDto(Subscription subscription, long userId) {
        this.id = subscription.getId();
        this.filter = new FilterDto(subscription.getFilter());
        this.title = subscription.getTitle();
        this.userId = userId;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SubscriptionDto that = (SubscriptionDto) o;
        return Objects.equals(title, that.title) && Objects.equals(filter, that.filter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, filter);
    }
}
