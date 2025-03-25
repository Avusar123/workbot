package com.workbot.workbot.telegram.cache.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.workbot.workbot.telegram.cache.details.MultistepCacheDetails;

public class PendingModel extends CachedActionModel {
    public PendingModel(String sender,
                        MultistepCacheDetails actionDetails) {
        super(sender, actionDetails);
    }

    protected PendingModel() {
        super("", null);
    }

    @JsonIgnore
    public int getCurrentStep() {
        return ((MultistepCacheDetails)actionDetails).getCurrentStep();
    }

    @JsonIgnore
    public boolean isFullCompleted() {
        return ((MultistepCacheDetails)actionDetails).isCompleted();
    }
}
