package com.example.backend.model.writing;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "writing_result")
public class WritingResult {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "member_id")
    private Integer memberId;

    @Column(name = "assigned_member_id")
    private Integer assignedMemberId;

    @Column(name = "writing_id")
    private Integer writingId;

    @Column(name = "score")
    private Integer score;

    @Column(name = "answer_text")
    private String answerText;

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
        this.score = score;
    }
}
