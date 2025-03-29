package com.workbot.workbot.telegram.newapi.setup.intent;

import com.workbot.workbot.telegram.newapi.setup.context.data.CacheData;
import com.workbot.workbot.telegram.newapi.setup.redis.PaginationContext;

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
