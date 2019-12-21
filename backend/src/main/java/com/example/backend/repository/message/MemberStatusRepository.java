package com.example.backend.repository.message;

import com.example.backend.model.member.MemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberStatusRepository extends JpaRepository<MemberStatus, Integer> {

    MemberStatus getByMemberIdAndAndLangId(int memberId, int langId);
}
