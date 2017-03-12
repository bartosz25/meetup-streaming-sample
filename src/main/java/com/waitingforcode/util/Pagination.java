package com.waitingforcode.util;

public class Pagination {

    private final int start;

    private final int max;

    public Pagination(int start, int max) {
        this.start = start;
        this.max = max;
    }

    public int getStart() {
        return start;
    }

    public int getMax() {
        return max;
    }
}
