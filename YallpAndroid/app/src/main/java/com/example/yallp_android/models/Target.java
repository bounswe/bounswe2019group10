package com.example.yallp_android.models;

public class Target {
    private Selector selector;
    private String source;

    public Selector getSelector() {
        return selector;
    }

    public void setSelector(Selector selector) {
        this.selector = selector;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Target(Selector selector, String source) {
        this.selector = selector;
        this.source = source;
    }
}
