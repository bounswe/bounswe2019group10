package com.example.backend.model.writing;


import javax.persistence.*;

@Entity
@Table(name = "writing_result")
public class WritingResult {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    @SequenceGenerator(name="id_generator", sequenceName = "writing_result_id_seq", allocationSize = 1)
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

    @Column(name = "is_scored")
    private boolean isScored;

    @Column(name = "writing_name")
    private String writingName;

    @Column(name = "assignment_date")
    private String assignmentDate;

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

    public String getAssignmentDate() {
        return assignmentDate;
    }

    public void setAssignmentDate(String assignmentDate) {
        this.assignmentDate = assignmentDate;
    }
}
