package com.example.backend.model.writing;

public class WritingResultDTO {

    private int id;

    private Integer memberId;

    private Integer assignedMemberId;

    private Integer writingId;

    private Integer score;

    private String answerText;

    private boolean isScored;

    private String writingName;

    private String assignedMemberName;

    private String memberName;

    private String imageUrl;

    private boolean isImage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getAssignedMemberId() {
        return assignedMemberId;
    }

    public void setAssignedMemberId(Integer assignedMemberId) {
        this.assignedMemberId = assignedMemberId;
    }

    public Integer getWritingId() {
        return writingId;
    }

    public void setWritingId(Integer writingId) {
        this.writingId = writingId;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        if(score == null)
            this.score =0;
        else
            this.score = score;
    }

    public boolean isScored() {
        return isScored;
    }

    public void setScored(boolean scored) {
        isScored = scored;
    }

    public String getWritingName() {
        return writingName;
    }

    public void setWritingName(String writingName) {
        this.writingName = writingName;
    }

    public String getAssignedMemberName() {
        return assignedMemberName;
    }

    public void setAssignedMemberName(String assignedMemberName) {
        this.assignedMemberName = assignedMemberName;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isImage() {
        return isImage;
    }

    public void setImage(boolean image) {
        isImage = image;
    }
}
