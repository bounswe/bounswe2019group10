package com.example.backend.model.message;

import javax.persistence.*;

@Entity
@Table(name = "conversation")
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    @SequenceGenerator(name="id_generator", sequenceName = "conversation_id_seq", allocationSize = 1)
    @Column(name = "id")
    private int id;

    @Column(name = "member1_id")
    private int member1Id;

    @Column(name = "member2_id")
    private int member2Id;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMember1Id() {
        return member1Id;
    }

    public void setMember1Id(int member1Id) {
        this.member1Id = member1Id;
    }

    public int getMember2Id() {
        return member2Id;
    }

    public void setMember2Id(int member2Id) {
        this.member2Id = member2Id;
    }
}

