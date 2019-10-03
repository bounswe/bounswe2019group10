package com.example.backend.service;


import com.example.backend.domain.Member;
import com.example.backend.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;
    public Member getMember(int id){
        return memberRepository.findById(id).get();
    }
    public Member createMember(String nickname, String password, String bio, String mail, boolean isExpert){
        Member member = new Member(isExpert, bio, password, nickname, mail);
        return memberRepository.save(member);
    }

}

