package com.workbot.workbot.telegram.cache.pending;

import com.workbot.workbot.telegram.cache.CachedActionModel;
import com.workbot.workbot.telegram.cache.details.CacheDetails;

import java.io.Serializable;

public class PendingModel extends CachedActionModel {
    private int currentStep;

    private final int totalSteps;

    public PendingModel(String sender,
                        CacheDetails actionDetails,
                        int totalSteps) {
        super(sender, actionDetails);
        this.totalSteps = totalSteps;
        currentStep = 0;
    }

    public void completeStep() {
        currentStep++;
    }

    public boolean isFullCompleted() {
        return currentStep == totalSteps;
    }
}
