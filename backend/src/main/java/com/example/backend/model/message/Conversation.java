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

    @Column(name = "is_member1_read")
    private boolean isMember1Read;

    @Column(name = "is_member2_read")
    private boolean isMember2Read;

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


    public boolean isMember1Read() {
        return isMember1Read;
    }

    public void setMember1Read(boolean member1Read) {
        isMember1Read = member1Read;
    }

    public boolean isMember2Read() {
        return isMember2Read;
    }

    public void setMember2Read(boolean member2Read) {
        isMember2Read = member2Read;
    }
}

