//package com.workbot.workbot.telegram.old.event.cache;
//
//import com.workbot.workbot.telegram.newapi.redis.model.PaginationModel;
//import com.workbot.workbot.telegram.old.event.update.CallbackRecieved;
//import com.workbot.workbot.telegram.old.event.update.CallbackType;
//import org.telegram.telegrambots.meta.api.objects.Update;
//
//public class PaginationCalledEvent extends CallbackRecieved {
//    private final PaginationModel model;
//
//    private final int targetPage;
//
//    public PaginationCalledEvent(Object source, Update update, PaginationModel model, int targetPage) {
//        super(source, update, CallbackType.PAGINATION_CHANGE, "");
//        this.model = model;
//        this.targetPage = targetPage;
//    }
//
//    public PaginationModel getModel() {
//        return model;
//    }
//
//    public int getTargetPage() {
//        return targetPage;
//    }
//}
