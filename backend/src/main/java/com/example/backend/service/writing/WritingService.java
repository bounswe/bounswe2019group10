package com.example.backend.service.writing;

import com.example.backend.model.member.Member;
import com.example.backend.model.member.MemberLanguage;
import com.example.backend.model.writing.Writing;
import com.example.backend.model.writing.WritingDTO;
import com.example.backend.repository.language.LanguageRepository;
import com.example.backend.repository.member.MemberLanguageRepository;
import com.example.backend.repository.member.MemberRepository;
import com.example.backend.repository.writing.WritingRepository;
import com.example.backend.service.dtoconverterservice.WritingDTOConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WritingService {
    @Autowired
    private WritingRepository writingRepository;


    @Autowired
    private MemberLanguageRepository memberLanguageRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private WritingDTOConverterService writingDTOConverterService;

    public WritingDTO getById(int id, String memberUsername) {
        Integer memberId = memberRepository.findByUsername(memberUsername).getId();
        Writing writing = writingRepository.getOne(id);
        List<String> evaluatorUsernames =  RecommendUsers(memberId, writing.getLanguageId());
        return writingDTOConverterService.apply(writing, evaluatorUsernames);
    }

    public List<String> RecommendUsers(Integer curMemberId, Integer languageId){
        List<String> users = new ArrayList<>();
        List<MemberLanguage> memberLanguages = memberLanguageRepository.get10ForWriting(languageId, curMemberId);
        for(MemberLanguage m: memberLanguages){
            users.add( memberRepository.getOne(m.getId()).getUsername() );
        }
        return users;
    }

}
