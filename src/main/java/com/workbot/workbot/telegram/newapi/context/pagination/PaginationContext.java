package com.workbot.workbot.telegram.newapi.context.pagination;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.workbot.workbot.telegram.newapi.context.resolver.type.Handler;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
public abstract class PaginationContext {
    private int currentPage;

    private int pageSize;

    public PaginationContext(int currentPage, int pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public PaginationContext(int currentPage) {
        this(currentPage, Paginations.PAGE_SIZE);
    }

    private PaginationContext() {}

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
