package com.workbot.workbot.telegram.setup.intent;

import com.workbot.workbot.telegram.setup.redis.PaginationContext;

public class PaginationUpdateIntent extends MessageUpdateIntent {
    private final PaginationContext paginationContext;

    private final int targetPage;

    public PaginationUpdateIntent(int messageId,
                                  long userId,
                                  PaginationContext paginationContext,
                                  int targetPage) {
        super(messageId, userId);
        this.paginationContext = paginationContext;
        this.targetPage = targetPage;
    }

    public PaginationContext getPaginationContext() {
        return paginationContext;
    }

    public int getTargetPage() {
        return targetPage;
    }
}
