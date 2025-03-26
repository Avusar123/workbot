package com.workbot.workbot.telegram.newapi.context.pagination;

import com.workbot.workbot.telegram.newapi.context.resolver.type.Action;
import com.workbot.workbot.telegram.newapi.context.resolver.type.Handler;

public class PaginationCallbackContext extends PaginationContext {
    private Handler handler;

    private Action action;

    protected PaginationCallbackContext() {
        super(0, 0);
    }

    public PaginationCallbackContext(Handler handler, Action action, int currentPage) {
        super(currentPage);
        this.handler = handler;
        this.action = action;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
