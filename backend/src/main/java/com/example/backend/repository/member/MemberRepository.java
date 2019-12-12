package com.example.backend.repository.member;


import com.example.backend.model.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

    Member findByMail(String mail);
    Member findByUsername(String username);

    @Query("SELECT m FROM Member m WHERE m.username LIKE %:username%")
    List<Member> searchByUsernameIncludes(@Param("username") String username);

    List<Member> findByUsernameContainingIgnoreCase(String username);



}