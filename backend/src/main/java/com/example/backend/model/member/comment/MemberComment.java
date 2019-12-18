package com.example.backend.model.member.comment;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "member_comment")
public class MemberComment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_comment_id_generator")
    @SequenceGenerator(name="member_comment_id_generator", sequenceName = "member_comment_id_seq", allocationSize = 1)
    @Column(name = "id")
    private int id;

    @Column(name = "member_id", nullable = false)
    private Integer memberId;

    @Column(name = "commentator_id", nullable = false)
    private int commentatorId;

    @Column(name = "comment_text")
    private String comment;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "rating")
    private int rating;

    public MemberComment(){ }

    public MemberComment(int memberId, int commentatorId, String comment){
        this.memberId = memberId;
        this.commentatorId = commentatorId;
        this.comment = comment;
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

    public int getCommentatorId() {
        return commentatorId;
    }

    public void setCommentatorId(int commentatorId) {
        this.commentatorId = commentatorId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
