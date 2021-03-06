package com.example.backend.model.member;

import com.example.backend.model.language.Language;
import com.example.backend.model.language.LevelName;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;

@Entity
@TypeDefs({
        @TypeDef(
                name = "string-array",
                typeClass = StringArrayType.class
        )})
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id")
    private Language language;

    @Enumerated(EnumType.STRING)
    @Column(name = "level_name")
    private LevelName levelName;

    @Type( type = "string-array" )
    @Column(name = "unresolved_dates",
            columnDefinition = "text[]")
    private String[] unresolvedDates;

    @Column(name = "progress")
    private int progress;

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public LevelName getLevelName() {
        return levelName;
    }

    public void setLevelName(LevelName levelName) {
        this.levelName = levelName;
    }

    public MemberLanguage(){}

    public MemberLanguage(int memberId, Language language){
        this.setMemberId(memberId);
        this.setLanguage(language);
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

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String[] getUnresolvedDates() {
        return unresolvedDates;
    }

    public void setUnresolvedDates(String[] unresolvedDates) {
        this.unresolvedDates = unresolvedDates;
    }
}
