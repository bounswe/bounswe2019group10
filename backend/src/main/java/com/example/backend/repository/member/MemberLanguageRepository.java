package com.example.backend.repository.member;

import com.example.backend.model.member.MemberLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberLanguageRepository extends JpaRepository<MemberLanguage, Integer> {
    MemberLanguage getByMemberId(int memberId);
}
