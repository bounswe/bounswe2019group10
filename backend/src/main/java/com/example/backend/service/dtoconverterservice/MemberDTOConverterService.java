package com.example.backend.service.dtoconverterservice;

import com.example.backend.model.member.Member;
import com.example.backend.model.member.MemberDTO;
import org.springframework.stereotype.Component;

@Component
public class MemberDTOConverterService {

    public MemberDTO apply(Member member){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setUsername(member.getUsername());
        memberDTO.setMail(member.getMail());
        memberDTO.setPassword(member.getPassword());
        memberDTO.setName(member.getName());
        memberDTO.setSurname(member.getSurname());
        memberDTO.setId(member.getId());
        memberDTO.setBio(member.getBio());
        memberDTO.setNativeLanguage(member.getNativeLanguage());
        return memberDTO;
    }
}
