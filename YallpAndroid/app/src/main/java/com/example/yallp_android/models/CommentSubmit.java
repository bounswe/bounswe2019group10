package com.example.yallp_android.models;

public class CommentSubmit {
    private String comment;
    private int memberId;
    private double rating;

    public CommentSubmit(String comment, int memberId, double rating) {
        this.comment = comment;
        this.memberId = memberId;
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public int getMemberId() {
        return memberId;
    }

    public double getRating() {
        return rating;
    }
}
