//package com.workbot.workbot.telegram.old.event.update;
//
//import org.telegram.telegrambots.meta.api.objects.Update;
//
//public class CallbackRecieved extends TelegramUpdateRecievedEvent {
//    CallbackType type;
//
//    String payload;
//
//    public CallbackRecieved(Object source, Update update, CallbackType type, String payload) {
//        super(source, update);
//
//        this.type = type;
//
//        this.payload = payload;
//    }
//
//    public CallbackRecieved(Object source, Update update, CallbackType type) {
//        super(source, update);
//
//        this.type = type;
//    }
//
//    public CallbackType getType() {
//        return type;
//    }
//
//    public boolean hasPayload() {
//        return payload != null && !payload.isEmpty() && !payload.isBlank();
//    }
//
//    public String getPayload() {
//        if (!hasPayload()) {
//            throw new UnsupportedOperationException("This callback type does not have payload");
//        }
//        return payload;
//    }
//}
