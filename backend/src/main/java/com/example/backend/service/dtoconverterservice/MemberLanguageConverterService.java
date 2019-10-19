package com.example.backend.service.dtoconverterservice;

import com.example.backend.model.quiz.Language;
import com.example.backend.model.quiz.MemberLanguage;
import com.example.backend.model.quiz.MemberLanguageDTO;
import com.example.backend.repository.language.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class MemberLanguageConverterService {

    @Autowired
    private LanguageRepository languageRepository;

    public MemberLanguageDTO apply(MemberLanguage memberLanguage) {
        MemberLanguageDTO memberLanguageDTO = new MemberLanguageDTO();
        memberLanguageDTO.setId(memberLanguage.getId());
        memberLanguageDTO.setLanguageId(memberLanguage.getLanguage().getId());
        memberLanguageDTO.setLanguageName(memberLanguage.getLanguage().getLanguageName());
        memberLanguageDTO.setMemberId(memberLanguage.getMemberId());
        return memberLanguageDTO;
    }
}
