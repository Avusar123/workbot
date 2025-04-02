package com.workbot.workbot.telegram.setup.event;

import com.workbot.workbot.telegram.setup.intent.UpdateIntent;
import org.springframework.context.ApplicationEvent;

public class CustomIntentEvent extends ApplicationEvent {
    private final UpdateIntent intent;

    public CustomIntentEvent(Object source, UpdateIntent intent) {
        super(source);
        this.intent = intent;
    }

    public UpdateIntent getIntent() {
        return intent;
    }
}
