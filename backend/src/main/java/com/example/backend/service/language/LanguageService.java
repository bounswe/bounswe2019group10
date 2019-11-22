package com.example.backend.service.language;

import com.example.backend.model.language.Language;
import com.example.backend.model.member.MemberLanguage;
import com.example.backend.repository.language.LanguageRepository;
import com.example.backend.repository.member.MemberLanguageRepository;
import com.example.backend.service.member.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageService {

    @Autowired
    LanguageRepository languageRepository;

    @Autowired
    MemberLanguageRepository memberLanguageRepository;

    @Autowired
    JwtUserDetailsService jwtUserDetailsService;



    public List<Language> findAll() {
        return languageRepository.findAll();
    }

    public int getLanguageLevel(int languageId){

        int memberId = jwtUserDetailsService.getByUsername(jwtUserDetailsService.getUsername()).getId();
        MemberLanguage memberLanguage = memberLanguageRepository
                .getByMemberIdAndLanguage(memberId, languageRepository.getById(languageId));

        return memberLanguage.getLanguageLevel();

    }

}
