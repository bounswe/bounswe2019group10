package com.example.backend.repository.message;

import com.example.backend.model.member.MemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberStatusRepository extends JpaRepository<MemberStatus, Integer> {

    MemberStatus getByMemberIdAndLanguageId(int memberId, int langId);
}
