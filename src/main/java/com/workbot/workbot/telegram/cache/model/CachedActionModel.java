package com.workbot.workbot.telegram.cache.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.workbot.workbot.telegram.cache.details.CacheDetails;
import com.workbot.workbot.telegram.cache.details.EmptyDetails;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
public class CachedActionModel {
    protected String sender;

    protected CacheDetails actionDetails;

    public CachedActionModel(String sender, CacheDetails actionDetails) {
        this.sender = sender;
        this.actionDetails = actionDetails;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public boolean hasDetails() {
        return actionDetails != null;
    }

    public CacheDetails getActionDetails() {
        if (!hasDetails()) {
            return new EmptyDetails();
        }
        return actionDetails;
    }

    public void setActionDetails(CacheDetails actionDetails) {
        this.actionDetails = actionDetails;
    }
}
