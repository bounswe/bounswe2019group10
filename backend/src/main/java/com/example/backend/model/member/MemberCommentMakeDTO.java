package com.example.backend.model.member;

public class MemberCommentMakeDTO {

    private int memberId;

    private String comment;

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
