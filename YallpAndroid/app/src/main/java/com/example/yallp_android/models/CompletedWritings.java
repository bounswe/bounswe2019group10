package com.example.yallp_android.models;

public class CompletedWritings {

    private String answerText;
    private int assignedMemberId;
    private int id;
    private int memberId;
    private String memberName;
    private int score;
    private boolean scored;
    private boolean image;
    private int writingId;
    private String writingName;
    private String imageUrl;
    private String assignedMemberName;

    public CompletedWritings(String answerText, int assignedMemberId, int id, int memberId, int score,
                             boolean scored,boolean image, int writingId,String writingName,String assignedMemberName,String memberName,
                             String imageUrl) {
        this.answerText = answerText;
        this.assignedMemberId = assignedMemberId;
        this.assignedMemberName = assignedMemberName;
        this.id = id;
        this.memberId = memberId;
        this.memberName =memberName;
        this.score = score;
        this.scored = scored;
        this.writingId = writingId;
        this.writingName = writingName;
        this.image = image;
        this.imageUrl = imageUrl;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getAssignedMemberName() {
        return assignedMemberName;
    }

    public void setAssignedMemberName(String assignedMemberName) {
        this.assignedMemberName = assignedMemberName;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public int getAssignedMemberId() {
        return assignedMemberId;
    }

    public void setAssignedMemberId(int assignedMemberId) {
        this.assignedMemberId = assignedMemberId;
    }

    public String getWritingName() {
        return writingName;
    }

    public void setWritingName(String writingName) {
        this.writingName = writingName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isScored() {
        return scored;
    }

    public void setScored(boolean scored) {
        this.scored = scored;
    }

    public int getWritingId() {
        return writingId;
    }

    public void setWritingId(int writingId) {
        this.writingId = writingId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImage(boolean image) {
        this.image = image;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isImage() {
        return image;
    }
}
