package com.example.backend.model.member;


import javax.persistence.*;

@Entity
@Table(name = "member_language")
public class MemberLanguage {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_language_id_generator")
    @SequenceGenerator(name="member_language_id_generator", sequenceName = "member_language_id_seq", allocationSize = 1)
    @Column(name = "id")
    private int id;

    @Column(name = "language_level", nullable = false)
    private int languageLevel;

    @Column(name = "member_id", nullable = false)
    private Integer memberId;

    @Column(name = "language_id", nullable = false)
    private Integer languageId;

    public MemberLanguage(){}

    public MemberLanguage(int memberId, int languageId){
        this.setMemberId(memberId);
        this.setLanguageId(languageId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLanguageLevel() {
        return languageLevel;
    }

    public void setLanguageLevel(int languageLevel) {
        this.languageLevel = languageLevel;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }
}
