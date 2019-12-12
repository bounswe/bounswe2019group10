package com.example.yallp_android.models;

public class Comment {
    private String comment;
    private int commentatorId;
    private String createdAt;
    private int id;
    private int memberId;
    private String updatedAt;

    public Comment(String comment, int commentatorId, String createdAt, int id, int memberId, String updatedAt) {
        this.comment = comment;
        this.commentatorId = commentatorId;
        this.createdAt = createdAt;
        this.id = id;
        this.memberId = memberId;
        this.updatedAt = updatedAt;
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
}
