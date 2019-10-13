package com.example.backend.repository.member;


import com.example.backend.model.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

    Member findByMail(String mail);
    Member findByUsername(String username);


}