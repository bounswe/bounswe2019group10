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
import java.util.HashMap;
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

    public WritingIsSolvedResponse getById(int id, String username) {
        Integer memberId = memberRepository.findByUsername(username).getId();
        Writing writing = writingRepository.getOne(id);
        WritingDTO writingDTO = writingDTOConverterService.apply(writing);
        WritingResult wr = writingResultRepository.findByWritingIdAndMemberId(writing.getId(), memberId);
        WritingIsSolvedResponse response = new WritingIsSolvedResponse();
        response.setWritingDTO(writingDTO);
        response.setSolved(wr!=null);
        if(wr!=null){
            response.setWritingResultDTO(writingResultDTOConverterService.apply(wr));
        }
        else{
            response.setWritingResultDTO(null);
        }
        return response;
    }

    public WritingResponse getAndRecommendById(int id, String memberUsername) {
        Integer memberId = memberRepository.findByUsername(memberUsername).getId();
        Writing writing = writingRepository.getOne(id);
        WritingDTO writingDTO = writingDTOConverterService.apply(writing);
        List<String> usernames = RecommendUsers(memberId, writing.getLanguageId());

        WritingResponse response = new WritingResponse();
        response.setWritingDTO(writingDTO);
        response.setUsernames(usernames);
        return response;
    }

    public List<String> RecommendUsers(Integer curMemberId, Integer languageId) {
        List<String> users = new ArrayList<>();
        List<MemberLanguage> memberLanguages = memberLanguageRepository.get10ForWriting(languageId, curMemberId);
        for (MemberLanguage m : memberLanguages) {
            users.add(memberRepository.getOne(m.getMemberId()).getUsername());
        }
        return users;
    }

    public List<Integer> getWritingIDList(List<Writing> writings) {
        List<Integer> ids = new ArrayList<>();
        for (Writing w : writings) {
            ids.add(w.getId());
        }
        return ids;
    }

    public List<Integer> getWritingsInLanguage(Integer languageId) {
        return getWritingIDList(writingRepository.findAllByLanguageId(languageId));
    }

    public List<WritingIsSolvedResponse> getWritingsInLanguageJson(Integer languageId, String username) {
        int memberId = memberRepository.findByUsername(username).getId();
        List<WritingIsSolvedResponse> writingIsSolvedResponseList = new ArrayList<>();
        List<Writing> writings = writingRepository.findAllByLanguageId(languageId);
        List<WritingResultDTO> results = getWritingResultsOfMember(username);
        HashMap<Integer, WritingResultDTO> map = new HashMap<>(); //writingId, WritingResultDTO
        for(WritingResultDTO wr: results){
            map.put(wr.getWritingId(), wr);
        }
        for(Writing w: writings){
            WritingIsSolvedResponse ws = new WritingIsSolvedResponse();
            ws.setWritingDTO(writingDTOConverterService.apply(w));
            WritingResultDTO wr = map.get(w.getId());
            ws.setSolved(wr!=null);
            ws.setWritingResultDTO(wr);
            writingIsSolvedResponseList.add(ws);
        }
        return writingIsSolvedResponseList;
    }

    public List<WritingResultDTO> getWritingResultsOfMember(String username) {
        Integer memberId = memberRepository.findByUsername(username).getId();
        List<WritingResult> writingResults = writingResultRepository.findAllByMemberId(memberId);
        List<WritingResultDTO> writingResultDTOS = new ArrayList<>();
        writingResults.forEach(writingResult -> writingResultDTOS.add(writingResultDTOConverterService.apply(writingResult)));
        return writingResultDTOS;
    }

    public WritingResultDTO processWritingAnswer(WritingRequest writingRequest, String username, int writingId) {
        Member evMember = memberRepository.findByUsername(writingRequest.getEvaluatorUsername());
        Writing writing = writingRepository.getOne(writingId);
        if (writingRequest.getEvaluatorUsername() == null || evMember == null || evMember.getUsername().equals(username)) {
            return null;
        }

        //Now add the writing result to the table
        WritingResult writingResult = new WritingResult();
        writingResult.setAnswerText(writingRequest.getAnswerText());
        writingResult.setAssignedMemberId(evMember.getId());
        writingResult.setMemberId(memberRepository.findByUsername(username).getId());
        writingResult.setWritingId(writingRequest.getWritingId());
        writingResult.setWritingName(writing.getWritingName());
        writingResultRepository.save(writingResult);
        return writingResultDTOConverterService.apply(writingResult);
    }

    public List<WritingResultDTO> findAllCompleteByAssignedId(String username) {
        Integer assignedMemberId = memberRepository.findByUsername(username).getId();
        List<WritingResult> writingResults = writingResultRepository.findAllCompleteByAssignedId(assignedMemberId);
        List<WritingResultDTO> writingResultDTOS = new ArrayList<>();
        writingResults.forEach(writingResult -> writingResultDTOS.add(writingResultDTOConverterService.apply(writingResult)));
        return writingResultDTOS;
    }

    public List<WritingResultDTO> findAllNonCompleteByAssignedId(String username) {
        Integer assignedMemberId = memberRepository.findByUsername(username).getId();
        List<WritingResult> writingResults = writingResultRepository.findAllNonCompleteByAssignedId(assignedMemberId);
        List<WritingResultDTO> writingResultDTOS = new ArrayList<>();
        writingResults.forEach(writingResult -> writingResultDTOS.add(writingResultDTOConverterService.apply(writingResult)));
        return writingResultDTOS;
    }

    public WritingResultDTO findAssignmentById(String username, int writingResultId) {
        WritingResult writingResult = writingResultRepository.getOne(writingResultId);
        if (writingResult == null || writingResult.getAssignedMemberId() != memberRepository.findByUsername(username).getId()) {
            return null; //TODO add error message
        }
        return writingResultDTOConverterService.apply(writingResult);
    }

    public WritingResultDTO evaluateWriting(String username, Integer writingResultId, Integer score) {
        //Check if the user is assigned to that writing first. If not return warning message
        WritingResult writingResult = writingResultRepository.getOne(writingResultId);
        if (writingResult == null) {
            return null;
        } else if (writingResult.getAssignedMemberId() != memberRepository.findByUsername(username).getId()) {
            return null;
        }
        //If so update the writing result and return "success"
        writingResult.setScore(score);
        writingResult.setScored(true);
        writingResultRepository.save(writingResult);

        return writingResultDTOConverterService.apply(writingResult);
    }

    public String getUsernameFromId(Integer id){
        return memberRepository.getOne(id).getUsername();
    }

}
