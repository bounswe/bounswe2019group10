package com.example.backend.service;


import com.example.backend.domain.Member;
import com.example.backend.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    public Member getMember(int id){
        return memberRepository.getOne(id);
    }

    public Member getMember(String name, String pass){
        return memberRepository.getByNameAndPassword(name, pass);
    }

    public Member createMember(String nickname, String password, String mail, String bio, Boolean isExpert){
        Member member = new Member(nickname, password, mail, bio, isExpert);
        return memberRepository.save(member);
    }

    public Member createAccount(String nickname, String password){
        Member member = new Member(nickname, password);
        return memberRepository.save(member);
    }

    public void deleteMember(int id){
        memberRepository.deleteById(id);
    }

}

