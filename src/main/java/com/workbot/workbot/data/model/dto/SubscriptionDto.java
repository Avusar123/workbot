package com.workbot.workbot.data.model.dto;

import com.workbot.workbot.data.model.Subscription;
import com.workbot.workbot.data.model.dto.util.TelegramSafeString;

import java.util.Objects;

public class SubscriptionDto {
    int id;

    TelegramSafeString title;

    FilterDto filter;

    long userId;

    public SubscriptionDto(FilterDto filter) {
        this.filter = filter;
        this.userId = -1;
    }

    protected SubscriptionDto() {}

    public SubscriptionDto(Subscription subscription, long userId) {
        this.id = subscription.getId();
        this.filter = new FilterDto(subscription.getFilter());
        this.title = new TelegramSafeString(subscription.getTitle());
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public TelegramSafeString getTitle() {
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

    public void setTitle(String title) {
        this.title = new TelegramSafeString(title);
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }
}
