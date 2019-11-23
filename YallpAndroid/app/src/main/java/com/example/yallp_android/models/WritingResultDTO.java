package com.example.yallp_android.models;

public class WritingResultDTO {
    private int id;
    private int memberId;
    private int assignedMemberId;
    private int writingId;
    private int score;
    private boolean scored;
    private String answerText;
    private String writingName;

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

    public int getAssignedMemberId() {
        return assignedMemberId;
    }

    public void setAssignedMemberId(int assignedMemberId) {
        this.assignedMemberId = assignedMemberId;
    }

    public int getWritingId() {
        return writingId;
    }

    public void setWritingId(int writingId) {
        this.writingId = writingId;
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

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setWritingName(String writingName) {
        this.writingName = writingName;
    }

    public String getWritingName() {
        return writingName;
    }
}
