package com.workbot.workbot.telegram.event.telegram;

import com.workbot.workbot.telegram.pagination.PaginationModel;
import org.telegram.telegrambots.meta.api.objects.Update;

public class PaginationCalledEvent extends CallbackRecieved {
    private final PaginationModel model;

    private final int targetPage;

    public PaginationCalledEvent(Object source, Update update, PaginationModel model, int targetPage) {
        super(source, update, CallbackType.PAGINATION_CHANGE, "");
        this.model = model;
        this.targetPage = targetPage;
    }

    public PaginationModel getModel() {
        return model;
    }

    public int getTargetPage() {
        return targetPage;
    }
}
