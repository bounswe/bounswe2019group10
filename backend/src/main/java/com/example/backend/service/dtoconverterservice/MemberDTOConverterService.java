package com.example.backend.service.dtoconverterservice;

import com.example.backend.model.member.Member;
import com.example.backend.model.member.MemberDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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


    public List<MemberDTO> applyAll(List<Member> memberList){
        List<MemberDTO> list = new ArrayList<>();

        memberList.forEach(member -> {
            MemberDTO memberDTO = new MemberDTO();
            memberDTO.setUsername(member.getUsername());
            memberDTO.setMail(member.getMail());
            memberDTO.setPassword(member.getPassword());
            memberDTO.setName(member.getName());
            memberDTO.setSurname(member.getSurname());
            memberDTO.setId(member.getId());
            memberDTO.setBio(member.getBio());
            memberDTO.setNativeLanguage(member.getNativeLanguage());
            list.add(memberDTO);
        });

        return list;
    }


}
