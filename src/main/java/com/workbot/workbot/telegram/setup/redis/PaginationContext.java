package com.workbot.workbot.telegram.setup.redis;

import com.workbot.workbot.telegram.setup.intent.type.HandlerType;

public class PaginationContext {
    private int currentPage;

    private HandlerType handlerType;

    public PaginationContext(int currentPage, HandlerType handlerType) {
        this.currentPage = currentPage;
        this.handlerType = handlerType;
    }

    protected PaginationContext() {}

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public HandlerType getHandlerType() {
        return handlerType;
    }

    public void setHandlerType(HandlerType handlerType) {
        this.handlerType = handlerType;
    }
}
