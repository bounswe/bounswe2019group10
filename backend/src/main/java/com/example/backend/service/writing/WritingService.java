package com.example.backend.service.writing;

import com.example.backend.model.member.Member;
import com.example.backend.model.member.MemberLanguage;
import com.example.backend.model.writing.*;
import com.example.backend.repository.language.LanguageRepository;
import com.example.backend.repository.member.MemberLanguageRepository;
import com.example.backend.repository.member.MemberRepository;
import com.example.backend.repository.writing.WritingRepository;
import com.example.backend.repository.writing.WritingResultRepository;
import com.example.backend.service.dtoconverterservice.WritingDTOConverterService;
import com.example.backend.service.dtoconverterservice.WritingResultDTOConverterService;
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

    @Autowired
    private WritingResultDTOConverterService writingResultDTOConverterService;

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

    //We can't return all of the writings explicitly to the user because this will be too big.
    //Currently I am converting the writing list to id list of Integers. I could not manage the
    //repository to return this directly.
    public List<Integer> getWritingIDList(List<Writing> writings){
        List<Integer> ids = new ArrayList<>();
        for(Writing w: writings){
            ids.add(w.getId());
        }
        return ids;
    }

    public List<Integer> getWritingsInLanguage(Integer languageId){
        return getWritingIDList( writingRepository.findAllByLanguageId(languageId));
    }

    public List<WritingResultDTO> DTOListConverter(List<WritingResult> writingResults){
        List<WritingResultDTO> writingResultDTOS = new ArrayList<>();
        for(WritingResult w: writingResults){
            writingResultDTOS.add(writingResultDTOConverterService.apply(w));
        }
        return writingResultDTOS;
    }

    public List<WritingResultDTO> getWritingResultsOfMember(String username){
        Integer memberId = memberRepository.findByUsername(username).getId();
        return DTOListConverter(writingResultRepository.findAllByMemberId(memberId));
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

    public List<WritingResultDTO> findAllCompleteByAssignedId(String username){
        Integer assignedMemberId = memberRepository.findByUsername(username).getId();
        return DTOListConverter(writingResultRepository.findAllCompleteByAssignedId(assignedMemberId));
    }

    public List<WritingResultDTO> findAllNonCompleteByAssignedId(String username){
        Integer assignedMemberId = memberRepository.findByUsername(username).getId();
        return DTOListConverter(writingResultRepository.findAllNonCompleteByAssignedId(assignedMemberId));
    }

    public WritingResultDTO findAssignmentById(String username, int writingResultId){
        WritingResult writingResult  = writingResultRepository.getOne(writingResultId);
        if(writingResult==null || writingResult.getAssignedMemberId() != memberRepository.findByUsername(username).getId()){
            return null; //TODO add error message
        }
        return writingResultDTOConverterService.apply(writingResult);

    }

    public String evaluateWriting(String username, Integer writingResultId, Integer score){
        //Check if the user is assigned to that writing first. If not return warning message
        WritingResult writingResult  = writingResultRepository.getOne(writingResultId);
        if(writingResult==null){
            return "Writing evaluation ID does not exist.";
        }
        else if( writingResult.getAssignedMemberId() != memberRepository.findByUsername(username).getId()){
            return "The user does not have permission to evaluate the given writing.";
        }
        //IF so update the writing result and return "success"
        writingResult.setScore(score);
        writingResultRepository.save(writingResult);
        return "success";
    }



}
