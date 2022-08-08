package com.example.aksistestproje.data;

import com.google.gson.annotations.SerializedName;

public class MarvelData {

    private int offset, limit, total, count;

    @SerializedName("results")
    private MarvelResults[] marvelResults;

    public MarvelData(int offset, int limit, int total, int count, MarvelResults[] marvelResults) {
        this.offset = offset;
        this.limit = limit;
        this.total = total;
        this.count = count;
        this.marvelResults = marvelResults;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public MarvelResults[] getMarvelResults() {
        return marvelResults;
    }

    public void setMarvelResults(MarvelResults[] marvelResults) {
        this.marvelResults = marvelResults;
    }
}
