package com.example.backend.repository;

import com.example.backend.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {

    Member findByUsernameAndPassword(String username, String pass);
    Member findByUsername(String username);
    Member findByMailAndPassword(String mail, String password);
    Member findByMail(String mail);

}