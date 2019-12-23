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

    @Query("SELECT m FROM Member m WHERE LOWER(m.username) LIKE %:username% and not :myname =  m.username")
    List<Member> searchByUsernameIncludes(@Param("username") String username, @Param("myname") String myname);

    List<Member> findByUsernameContainingIgnoreCase(String username);




}