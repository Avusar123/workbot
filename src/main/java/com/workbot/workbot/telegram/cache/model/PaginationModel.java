package com.workbot.workbot.telegram.cache.model;

import com.workbot.workbot.telegram.cache.details.CacheDetails;

public class PaginationModel extends CachedActionModel {
    private int currentPage;

    private int pageSize;

    public PaginationModel(String sender, CacheDetails cacheDetails, int currentPage, int pageSize) {
        super(sender, cacheDetails);
        this.sender = sender;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public PaginationModel(String sender, int currentPage, int pageSize) {
        super(sender, null);
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
}
