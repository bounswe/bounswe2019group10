package com.example.yallp_android.models;

public class CompletedWritings {

    private String answerText;
    private int assignedMemberId;
    private int id;
    private int memberId;
    private int score;
    private boolean scored;
    private int writingId;
    private String writingName;

    public CompletedWritings(String answerText, int assignedMemberId, int id, int memberId, int score, boolean scored, int writingId,String writingName) {
        this.answerText = answerText;
        this.assignedMemberId = assignedMemberId;
        this.id = id;
        this.memberId = memberId;
        this.score = score;
        this.scored = scored;
        this.writingId = writingId;
        this.writingName = writingName;
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
}
