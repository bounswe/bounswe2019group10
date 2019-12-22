package com.example.yallp_android.models;

public class Selector {
    private int start;
    private int end;
    private String type;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Selector(int start, int end, String type) {
        this.start = start;
        this.end = end;
        this.type = type;
    }
}
