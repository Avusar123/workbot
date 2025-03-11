package com.workbot.workbot.logic.event;

import com.workbot.workbot.data.model.dto.SubscriptionDto;
import org.springframework.context.ApplicationEvent;

import java.util.List;

public class SubTriggeredEvent extends ApplicationEvent {
    private List<SubscriptionDto> subs;


    public SubTriggeredEvent(Object source, List<SubscriptionDto> subs) {
        super(source);
        this.subs = subs;
    }
}
