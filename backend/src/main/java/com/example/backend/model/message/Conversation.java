package com.example.backend.model.message;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "conversation")
public class Conversation {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "member1_id")
    private int member1_id;

    @Column(name = "member2_id")
    private int member2_id;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMember1_id() {
        return member1_id;
    }

    public void setMember1_id(int member1_id) {
        this.member1_id = member1_id;
    }

    public int getMember2_id() {
        return member2_id;
    }

    public void setMember2_id(int member2_id) {
        this.member2_id = member2_id;
    }
}

