package com.example.backend.model.member;

import javax.persistence.*;

@Entity
@Table(name = "member_quiz")
public class MemberQuiz {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_quiz_id_generator")
    @SequenceGenerator(name="member_quiz_id_generator", sequenceName = "member_quiz_id_seq", allocationSize = 1)
    @Column(name = "id")
    private int id;

    @Column(name = "member_id", nullable = false)
    private Integer memberId;

    @Column(name = "quiz_id", nullable = false)
    private Integer quizId;

    @Column(name = "score")
    private Integer score;

    public MemberQuiz(){}

    public MemberQuiz(int memberId, int quizId, int score){
        this.memberId = memberId;
        this.quizId = quizId;
        this.score = score;
    }

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

    public Integer getQuizId() {
        return quizId;
    }

    public void setQuizId(Integer quizId) {
        this.quizId = quizId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
