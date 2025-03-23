package com.workbot.workbot.telegram.cache;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.workbot.workbot.telegram.cache.details.CacheDetails;

import java.io.Serializable;
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
        return actionDetails == null;
    }

    public CacheDetails getActionDetails() {
        if (!hasDetails()) {
            throw new NullPointerException("There is no details");
        }
        return actionDetails;
    }

    public void setActionDetails(CacheDetails actionDetails) {
        this.actionDetails = actionDetails;
    }
}
