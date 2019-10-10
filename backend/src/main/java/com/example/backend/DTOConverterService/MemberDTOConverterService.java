package com.example.backend.DTOConverterService;

import com.example.backend.domain.Member;
import com.example.backend.dto.MemberDTO;
import org.springframework.stereotype.Component;

@Component
public class MemberDTOConverterService {

    public MemberDTO apply(Member member) {
        MemberDTO memberDTO = new MemberDTO();

        memberDTO.setId(member.getId());
        memberDTO.setMail(member.getMail());
        memberDTO.setUsername(member.getUsername());

        return memberDTO;
    }
}
