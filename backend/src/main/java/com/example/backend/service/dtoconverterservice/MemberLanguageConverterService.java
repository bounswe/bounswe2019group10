package com.example.backend.service.dtoconverterservice;

import com.example.backend.model.language.MemberLanguage;
import com.example.backend.model.language.MemberLanguageDTO;
import org.springframework.stereotype.Component;

@Component
class MemberLanguageConverterService {

    public MemberLanguageDTO apply(MemberLanguage memberLanguage) {
        MemberLanguageDTO memberLanguageDTO = new MemberLanguageDTO();
        memberLanguageDTO.setId(memberLanguage.getId());
        memberLanguageDTO.setLanguageId(memberLanguage.getLanguage().getId());
        memberLanguageDTO.setLanguageName(memberLanguage.getLanguage().getLanguageName());
        memberLanguageDTO.setMemberId(memberLanguage.getMemberId());
        return memberLanguageDTO;
    }
}
