package com.example.backend.repository;

import com.example.backend.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {

    Member getByNameAndPassword(String name, String pass);
    Member findByName(String name);
    Member findByMail(String mail);
}