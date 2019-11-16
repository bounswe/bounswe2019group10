package com.example.backend.service.writing;

import com.example.backend.model.member.Member;
import com.example.backend.model.member.MemberLanguage;
import com.example.backend.model.writing.Writing;
import com.example.backend.model.writing.WritingDTO;
import com.example.backend.model.writing.WritingRequest;
import com.example.backend.model.writing.WritingResult;
import com.example.backend.repository.language.LanguageRepository;
import com.example.backend.repository.member.MemberLanguageRepository;
import com.example.backend.repository.member.MemberRepository;
import com.example.backend.repository.writing.WritingRepository;
import com.example.backend.repository.writing.WritingResultRepository;
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
    private WritingResultRepository writingResultRepository;

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

    public List<Integer> getWritingsInLanguage(Integer languageId){
        return writingRepository.findAllByLanguageId(languageId);
    }

    public List<WritingResult> getWritingResultsOfMember(Integer memberId){
        return writingResultRepository.findAllByMemberId(memberId);
    }

    public String processWritingAnswer(WritingRequest writingRequest, String username){
        Member evMember = memberRepository.findByUsername(writingRequest.getEvaluatorUsername());
        if(writingRequest.getEvaluatorUsername()==null || evMember==null){
            return "Specify a valid username to evaluate the quiz";
        }

        //Now add the writing result to the table
        WritingResult writingResult = new WritingResult();
        writingResult.setAnswerText(writingRequest.getAnswerText());
        writingResult.setAssignedMemberId(evMember.getId());
        writingResult.setMemberId(memberRepository.findByUsername(username).getId());
        writingResult.setWritingId(writingRequest.getWritingId());
        writingResultRepository.save(writingResult);

        return "The answer is saved.";
    }

    public List<WritingResult> findAllCompleteByAssignedId(Integer assignedMemberId){
        return writingResultRepository.findAllCompleteByAssignedId(assignedMemberId);
    }

    public List<WritingResult> findAllNonCompleteByAssignedId(Integer assignedMemberId){
        return writingResultRepository.findAllNonCompleteByAssignedId(assignedMemberId);
    }

}
