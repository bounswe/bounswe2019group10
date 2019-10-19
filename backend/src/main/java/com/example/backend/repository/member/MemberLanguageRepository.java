package com.example.backend.repository.member;

import com.example.backend.model.member.MemberLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public interface MemberLanguageRepository extends JpaRepository<MemberLanguage, Integer> {
    MemberLanguage getByMemberId(int memberId);
}
