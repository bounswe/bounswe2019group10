package com.example.backend.service.writing;

import com.example.backend.model.member.Member;
import com.example.backend.model.member.MemberLanguage;
import com.example.backend.model.notification.Notification;
import com.example.backend.model.notification.NotificationType;
import com.example.backend.model.writing.*;
import com.example.backend.repository.language.LanguageRepository;
import com.example.backend.repository.member.MemberLanguageRepository;
import com.example.backend.repository.member.MemberRepository;
import com.example.backend.repository.writing.WritingRepository;
import com.example.backend.repository.writing.WritingResultRepository;
import com.example.backend.service.dtoconverterservice.WritingDTOConverterService;
import com.example.backend.service.dtoconverterservice.WritingResultDTOConverterService;
import com.example.backend.service.member.JwtUserDetailsService;
import com.example.backend.service.notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private WritingDTOConverterService writingDTOConverterService;

    @Autowired
    private WritingResultDTOConverterService writingResultDTOConverterService;

    @Autowired
    private NotificationService notificationService;

    public WritingIsSolvedResponse getById(int id) {
        Integer memberId = jwtUserDetailsService.getUserId();
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

    public WritingResponse getAndRecommendById(int id) {
        Integer memberId = jwtUserDetailsService.getUserId();
        Writing writing = writingRepository.getOne(id);
        WritingDTO writingDTO = writingDTOConverterService.apply(writing);
        List<String> usernames = RecommendUsers(memberId, writing.getLanguageId());

        WritingResponse response = new WritingResponse();
        response.setWritingDTO(writingDTO);
        response.setUsernames(usernames);
        return response;
    }

    public List<String> RecommendUsers(Integer curMemberId, Integer languageId) {
        ArrayList<String> users = new ArrayList<>();
        HashSet<String> usernames = new HashSet<>(); //For quick lookup
        HashMap<Integer, String> idUsernameMap = new HashMap<>();
        //This probably needs to change when a lot of new members come
        List<Member> members = memberRepository.findAll();
        members.forEach(member -> idUsernameMap.put(member.getId(), member.getUsername()));
        LocalDateTime curTime = LocalDateTime.now();
        List<MemberLanguage> memberLanguages = memberLanguageRepository.findByLanguageIdExceptMemberIdOrderByLanguageLevelDesc(languageId, curMemberId);
        int recommendationSize = Math.min(10, memberLanguages.size());
        int qualifiedSize = recommendationSize*3/4;
        int i = 0;
        //Add active and well qualified members that are not busy and not recently assigned a task
        for (; i<memberLanguages.size() && users.size() != recommendationSize; ++i) {
            MemberLanguage m = memberLanguages.get(i);
            String username = idUsernameMap.get(m.getMemberId());
            String[] dates = m.getUnresolvedDates();
            if(dates.length > 0){
                LocalDateTime earlyDate = LocalDateTime.parse(dates[0]);
                LocalDateTime recentDate = LocalDateTime.parse(dates[dates.length-1]);
                long inactiveDays = Duration.between(curTime, earlyDate).toDays();
                long recentAssignmentOffset = Duration.between(curTime, recentDate).toDays();
                if(inactiveDays<7 && recentAssignmentOffset > 0 && dates.length<5){
                    users.add(username);
                    usernames.add(username);
                }
            }
            else{
                users.add(username);
                usernames.add(username);
            }
        }
        //Add rest only if the member is not assigned any task.
        for(; i<memberLanguages.size() && users.size() != recommendationSize ; ++i){
            MemberLanguage m = memberLanguages.get(i);
            String username = idUsernameMap.get(m.getMemberId());
            if(m.getUnresolvedDates().length==0){
                users.add(username);
                usernames.add(username);
            }
        }

        //If there is still not enough members to be recommended take them in reverse order
        while(users.size()<recommendationSize && --i >= 0){
            MemberLanguage m = memberLanguages.get(i);
            String username = idUsernameMap.get(m.getMemberId());
            if(!usernames.contains(username)){
                users.add(username);
            }
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

    public List<WritingIsSolvedResponse> getWritingsInLanguageJson(Integer languageId) {
        int memberId = jwtUserDetailsService.getUserId();
        List<WritingIsSolvedResponse> writingIsSolvedResponseList = new ArrayList<>();
        List<Writing> writings = writingRepository.findAllByLanguageId(languageId);
        List<WritingResultDTO> results = getWritingResultsOfMember();
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

    public List<WritingResultDTO> getWritingResultsOfMember() {
        Integer memberId = jwtUserDetailsService.getUserId();
        List<WritingResult> writingResults = writingResultRepository.findAllByMemberId(memberId);
        List<WritingResultDTO> writingResultDTOS = new ArrayList<>();
        writingResults.forEach(writingResult -> writingResultDTOS.add(writingResultDTOConverterService.apply(writingResult)));
        return writingResultDTOS;
    }

    public String getWritingResultAnswerText(int id) {

        WritingResult writingResult = writingResultRepository.findById(id).orElse(null);

        if(writingResult == null)
            return null;

        return writingResult.getAnswerText();
    }

    public WritingResultDTO processWritingAnswer(WritingRequest writingRequest, String username, int writingId) {
        Member evMember = memberRepository.findByUsername(writingRequest.getEvaluatorUsername());
        Member curMember = memberRepository.findByUsername(username);
        Writing writing = writingRepository.findById(writingId).orElse(null);
        MemberLanguage memberLanguage = memberLanguageRepository.getByMemberIdAndLanguageId(evMember.getId(), writing.getLanguageId());
        LocalDateTime localDateTime = LocalDateTime.now();
        String curTime = localDateTime.toString();

        if (writingRequest.getEvaluatorUsername() == null || evMember == null || evMember.getUsername().equals(username)) {
            return null;
        }

        //Now add the writing result to the table
        WritingResult writingResult = writingResultRepository.findByWritingIdAndMemberId(writing.getId(), curMember.getId());
        if (writingResult == null){
            writingResult = new WritingResult();
        }
        Notification notification = new Notification();
        notification.setMemberId(evMember.getId());
        notification.setNotificationType(NotificationType.WRITING_EVALUATE);
        notification.setText("You have a new writing to evaluate!");
        notification.setRead(false);
        notificationService.save(notification);

        writingResult.setAnswerText(writingRequest.getAnswerText());
        writingResult.setAssignedMemberId(evMember.getId());
        writingResult.setMemberId(memberRepository.findByUsername(username).getId());
        writingResult.setWritingId(writingRequest.getWritingId());
        writingResult.setWritingName(writing.getWritingName());
        writingResult.setAssignmentDate(curTime);

        //Add to tail
        String oldstamps[] = memberLanguage.getUnresolvedDates();
        String timestamps[] = new String[ oldstamps.length + 1];
        System.arraycopy(oldstamps, 0, timestamps, 0, oldstamps.length);
        timestamps[oldstamps.length] = curTime;
        memberLanguage.setUnresolvedDates(timestamps);

        writingResultRepository.save(writingResult);
        return writingResultDTOConverterService.apply(writingResult);
    }

    public List<WritingResultDTO> findAllCompleteByAssignedId() {
        Integer assignedMemberId = jwtUserDetailsService.getUserId();
        List<WritingResult> writingResults = writingResultRepository.findAllCompleteByAssignedId(assignedMemberId);
        List<WritingResultDTO> writingResultDTOS = new ArrayList<>();
        writingResults.forEach(writingResult -> writingResultDTOS.add(writingResultDTOConverterService.apply(writingResult)));
        return writingResultDTOS;
    }

    public List<WritingResultDTO> findAllNonCompleteByAssignedId() {
        Integer assignedMemberId = jwtUserDetailsService.getUserId();
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
        Writing writing = writingRepository.findById(writingResult.getWritingId()).orElse(null);
        Member evMember = memberRepository.findByUsername(username);
        MemberLanguage memberLanguage = memberLanguageRepository.getByMemberIdAndLanguage(evMember.getId(), languageRepository.getById(writing.getLanguageId()));
        LocalDateTime localDateTime = LocalDateTime.now();
        Timestamp curTimeStamp = Timestamp.valueOf(localDateTime);

        if (writingResult == null) {
            return null;
        } else if (writingResult.getAssignedMemberId() != memberRepository.findByUsername(username).getId()) {
            return null;
        }
        //If so update the writing result and return "success"
        writingResult.setScore(score);
        writingResult.setScored(true);
        writingResultRepository.save(writingResult);

        //Remove from the head.
        String oldstamps[] = memberLanguage.getUnresolvedDates();
        String timestamps[] = new String[ oldstamps.length -1];
        boolean found = false;
        for(int i=0; i<oldstamps.length; ++i){
            if(oldstamps[i].equals(writingResult.getAssignmentDate())){
                found= true;
                continue;
            }
            if(found)
                timestamps[i-1] = oldstamps[i];
            else
                timestamps[i] = oldstamps[i];
        }
        memberLanguage.setUnresolvedDates(timestamps);

        Notification notification = new Notification();
        notification.setMemberId(writingResult.getMemberId());
        notification.setNotificationType(NotificationType.WRITING_RESULT);
        notification.setText("You have an evaluated writing!");
        notification.setRead(false);
        notificationService.save(notification);
        return writingResultDTOConverterService.apply(writingResult);
    }


    public WritingDTO addNewWriting(WritingDTO writingDTO){
        Writing writing = new Writing();
        writing.setLanguageId(writingDTO.getLanguageId());
        writing.setTaskText(writingDTO.getTaskText());
        writing.setWritingName(writingDTO.getWritingName());
        writingRepository.save(writing);
        return writingDTO;
    }

    public String getUsernameFromId(Integer id){
        return memberRepository.getOne(id).getUsername();
    }



}
