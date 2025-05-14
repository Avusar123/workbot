package com.workbot.workbot.telegram.setup.intent;

import com.workbot.workbot.telegram.setup.context.PaginationContext;

public class PaginationUpdateIntent extends MessageUpdateIntent {
    private final PaginationContext paginationContext;

    private final String queryId;

    private final int targetPage;

    public PaginationUpdateIntent(int messageId,
                                  long userId,
                                  PaginationContext paginationContext,
                                  String queryId, int targetPage) {
        super(messageId, userId);
        this.paginationContext = paginationContext;
        this.queryId = queryId;
        this.targetPage = targetPage;
    }

    public PaginationContext getPaginationContext() {
        return paginationContext;
    }

    public int getTargetPage() {
        return targetPage;
    }

    public String getQueryId() {
        return queryId;
    }
}
