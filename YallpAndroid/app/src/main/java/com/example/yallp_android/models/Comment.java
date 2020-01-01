package com.example.yallp_android.models;

import java.io.Serializable;

public class Comment implements Serializable {
    private String comment;
    private int commentatorId;
    private String commentatorName;
    private String createdAt;
    private int id;
    private int memberId;
    private String memberName;
    private String updatedAt;
    private double rating;

    public Comment(String comment, int commentatorId, String commentatorName, String createdAt, int id, int memberId, String memberName, String updatedAt, double rating) {
        this.comment = comment;
        this.commentatorId = commentatorId;
        this.commentatorName = commentatorName;
        this.createdAt = createdAt;
        this.id = id;
        this.memberId = memberId;
        this.memberName = memberName;
        this.updatedAt = updatedAt;
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public int getCommentatorId() {
        return commentatorId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public int getId() {
        return id;
    }

    public int getMemberId() {
        return memberId;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getCommentatorName() {
        return commentatorName;
    }

    public String getMemberName() {
        return memberName;
    }

    public double getRating() {
        return rating;
    }
}
