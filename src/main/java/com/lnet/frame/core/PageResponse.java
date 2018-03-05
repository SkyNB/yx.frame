package com.lnet.frame.core;

import java.util.List;

public class PageResponse<T> extends ListResponse<T> {

    private long page;
    private int pageSize;
    private long totalItemCount;

    public PageResponse() {
        super();
        this.page = 1;
    }

    public PageResponse(List<T> body, long page, int pageSize, long totalItemCount) {
        super(body, true, "");
        this.page = page;
        this.totalItemCount = totalItemCount;
        this.pageSize = pageSize;
    }

    //region override
    @Override
    public PageResponse<T> setBody(List<T> body) {
        this.clear();
        this.addAll(body);
        return this;
    }

    @Override
    public PageResponse<T> setSuccess(boolean success) {
        super.setSuccess(success);
        return this;
    }

    @Override
    public PageResponse<T> setMessage(String message) {
        super.setMessage(message);
        return this;
    }
    //endregion


    public long getPage() {
        return page;
    }

    public PageResponse<T> setPage(long page) {
        if (page < 1) throw new IllegalArgumentException("page不能小于1");
        this.page = page;
        return this;
    }

    public long getTotalItemCount() {
        return totalItemCount;
    }

    public PageResponse<T> setTotalItemCount(long totalItemCount) {
        if (totalItemCount < 0) throw new IllegalArgumentException("totalItemCount不能小于0");
        this.totalItemCount = totalItemCount;
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public PageResponse<T> setPageSize(int pageSize) {
        if (pageSize < 1) throw new IllegalArgumentException("pageSize不能小于1");
        this.pageSize = pageSize;
        return this;
    }

    public long getTotalPageCount() {
        if (totalItemCount > 0 && pageSize > 0)
            return (totalItemCount + pageSize - 1) / pageSize;
        return 0;
    }
}
