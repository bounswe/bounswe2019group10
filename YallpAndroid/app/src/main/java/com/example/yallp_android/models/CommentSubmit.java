package com.example.yallp_android.models;

public class CommentSubmit {
    private String comment;
    private int memberId;

    public CommentSubmit(String comment, int memberId) {
        this.comment = comment;
        this.memberId = memberId;
    }

    public String getComment() {
        return comment;
    }

    public int getMemberId() {
        return memberId;
    }
}
