package com.workbot.workbot.telegram.setup.context.data;

public class DelegatedCacheData extends CacheData {
    private CacheData inner;

    private int sourceMessageId;

    protected DelegatedCacheData() {}

    public DelegatedCacheData(CacheData inner, int sourceMessageId) {
        this.inner = inner;
        this.sourceMessageId = sourceMessageId;
    }

    public CacheData getInner() {
        return inner;
    }

    public int getSourceMessageId() {
        return sourceMessageId;
    }
}
