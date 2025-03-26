package com.workbot.workbot.telegram.newapi.processor.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateProcessor {
    @Autowired
    private PaginationProcessor paginationProcessor;

    @Autowired
    private MessageProcessor messageProcessor;

    @Autowired
    private ModelProcessor modelProcessor;

    public void process() {
        if (paginationProcessor.isPagination()) {
            paginationProcessor.process();
        } else if (modelProcessor.isModel()) {
            modelProcessor.process();
        } else if (messageProcessor.isMessage()){
            messageProcessor.process();
        } else {
            throw new UnsupportedOperationException("This update type is not supported");
        }
    }
}
