package com.workbot.workbot.logic.update.section.util;

import org.springframework.stereotype.Service;

@Service
public class UpdateStatusHolder {
    private boolean processing = false;

    public boolean isProcessing() {
        return processing;
    }

    public void setProcessing(boolean processing) {
        this.processing = processing;
    }
}
