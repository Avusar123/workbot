//package com.workbot.workbot.telegram.old.event.cache;
//
//import com.workbot.workbot.telegram.old.event.update.TelegramUpdateRecievedEvent;
//import org.telegram.telegrambots.meta.api.objects.Update;
//
//public class FilterRequiredEvent extends TelegramUpdateRecievedEvent {
//    private final String sender;
//
//    private final int messageId;
//
//    public FilterRequiredEvent(Object source, Update update, String sender, int messageId) {
//        super(source, update);
//        this.sender = sender;
//        this.messageId = messageId;
//    }
//
//    public String getSender() {
//        return sender;
//    }
//
//    public int getMessageId() {
//        return messageId;
//    }
//}