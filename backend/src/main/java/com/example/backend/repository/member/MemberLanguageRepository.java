package com.example.backend.repository.member;

import com.example.backend.model.language.Language;
import com.example.backend.model.member.MemberLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberLanguageRepository extends JpaRepository<MemberLanguage, Integer> {

    MemberLanguage getByMemberId(int memberId);

    MemberLanguage getByMemberIdAndLanguage(int memberId, Language language);

    //@Query(nativeQuery = true, value = "select * from member_language m where m.member_id = :mem_id and m.language_id = :lan_id")
    MemberLanguage getByMemberIdAndLanguageId(/*@Param("mem_id")*/ Integer memberId, /*@Param("lan_id")*/ Integer languageId);

    //Here number_of_assignments is a function defined in DB that returns number of uncomplete assignments for the user with the given ID.
    //We select the best 10 users in a specified language that have less than 5 waiting assignments.
    @Query(nativeQuery = true, value = "select  * from member_language m where m.member_id != :curMemId and m.language_id = :langId and number_of_assignments(m.member_id) < 5 order by language_level desc limit 10")
    List<MemberLanguage> get10ForWriting(@Param("langId") Integer langId, @Param("curMemId") Integer curMemId);

    @Query(nativeQuery = true, value = "select  * from member_language m where m.member_id != :curMemId and m.language_id = :langId order by language_level desc")
    List<MemberLanguage> findByLanguageIdExceptMemberIdOrderByLanguageLevelDesc(@Param("langId") Integer langId, @Param("curMemId")  Integer curMemId);

}
