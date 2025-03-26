package com.workbot.workbot.telegram.newapi.processor.processor;

import com.workbot.workbot.telegram.newapi.context.UpdateContextHolder;
import com.workbot.workbot.telegram.newapi.context.pagination.PaginationCallbackContext;
import com.workbot.workbot.telegram.newapi.context.pagination.Paginations;
import com.workbot.workbot.telegram.newapi.context.resolver.HandlerEntrypoint;
import com.workbot.workbot.telegram.newapi.context.resolver.type.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaginationProcessor {
    @Autowired
    private UpdateContextHolder holder;

    @Autowired
    private HandlerEntrypoint handlerEntrypoint;

    public boolean isPagination() {
        var updateContext = holder.getCurrent();

        if (updateContext.getUpdate().hasCallbackQuery()) {
            var callbackQuery = updateContext.getUpdate().getCallbackQuery();

            return Paginations.isPagination(callbackQuery.getData());
        }

        return false;
    }

    public void process() {
        var updateContext = holder.getCurrent();

        var callbackQuery = updateContext.getUpdate().getCallbackQuery();

        if (updateContext.getPaginationContext() instanceof PaginationCallbackContext paginationContext) {
            paginationContext.setCurrentPage(Paginations.getPage(callbackQuery.getData()));

            handlerEntrypoint.handle(paginationContext.getHandler(), State.PAGINATION, paginationContext.getAction());
        } else {
            throw new UnsupportedOperationException("Unable to proceed pagination callback while pagination is not set");
        }


    }
}
