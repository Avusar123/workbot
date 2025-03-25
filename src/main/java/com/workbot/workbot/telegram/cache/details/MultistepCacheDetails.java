package com.workbot.workbot.telegram.cache.details;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class MultistepCacheDetails extends CacheDetails {
    @JsonIgnore
    public abstract int getCurrentStep();

    @JsonIgnore
    public abstract boolean isCompleted();
}
