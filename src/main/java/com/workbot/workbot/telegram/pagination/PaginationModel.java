package com.workbot.workbot.telegram.pagination;

import com.workbot.workbot.telegram.pagination.details.ActionDetails;

import java.io.Serializable;

public class PaginationModel implements Serializable {
    private String sender;

    private ActionDetails actionDetails;

    private int currentPage;

    private int pageSize;

    public PaginationModel(String sender, ActionDetails actionDetails, int currentPage, int pageSize) {
        this.sender = sender;
        this.actionDetails = actionDetails;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public PaginationModel(String sender, int currentPage, int pageSize) {
        this.sender = sender;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public boolean hasDetails() {
        return actionDetails == null;
    }

    public ActionDetails getActionDetails() {
        if (!hasDetails()) {
            throw new NullPointerException("There is no details");
        }
        return actionDetails;
    }

    public void setActionDetails(ActionDetails actionDetails) {
        this.actionDetails = actionDetails;
    }
}
