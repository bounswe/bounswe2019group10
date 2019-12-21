package com.example.backend.service.quiz;

import com.example.backend.model.language.Language;
import com.example.backend.model.language.LevelName;
import com.example.backend.model.member.Member;
import com.example.backend.model.member.MemberLanguage;
import com.example.backend.model.member.MemberQuiz;
import com.example.backend.model.member.MemberStatus;
import com.example.backend.model.quiz.*;
import com.example.backend.repository.language.LanguageRepository;
import com.example.backend.repository.member.MemberLanguageRepository;
import com.example.backend.repository.member.MemberQuizRepository;
import com.example.backend.repository.member.MemberRepository;
import com.example.backend.repository.message.MemberStatusRepository;
import com.example.backend.repository.quiz.QuestionRepository;
import com.example.backend.repository.quiz.QuizRepository;
import com.example.backend.service.dtoconverterservice.QuestionDTOConverterService;
import com.example.backend.service.dtoconverterservice.QuizDTOConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private MemberStatusRepository memberStatusRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private MemberLanguageRepository memberLanguageRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private MemberQuizRepository memberQuizRepository;

    @Autowired
    private QuizDTOConverterService quizDTOConverterService;

    @Autowired
    private QuestionDTOConverterService questionDTOConverterService;

    public QuizDTO getById(int id) {
        Quiz quiz = quizRepository.getOne(id);
        List<Question> questions = questionRepository.getAllByQuizId(quiz.getId());

        List<QuestionDTO> questionDTOS = new ArrayList<>();

        questions.forEach(question -> questionDTOS.add(questionDTOConverterService.apply(question)));

        return quizDTOConverterService.apply(quiz, questionDTOS);
    }

    public List<QuizDTO> findAll() {
        List<QuizDTO> quizDTOS = new ArrayList<>();
        quizRepository.findAll().forEach(quiz -> quizDTOS.add(quizDTOConverterService.apply(quiz, null)));
        return quizDTOS;
    }

    public QuizRequest evaluateQuiz(QuizRequest quizRequest, String memberUname){
        //Take the request, add the necessary fields and send back
        Member curMember = memberRepository.findByUsername(memberUname);
        Quiz quiz = quizRepository.getOne(quizRequest.getQuizId());
        int score = 0;
        int quizId = quizRequest.getQuizId();
        List<Question> questions =  questionRepository.getAllByQuizId(quizId);
        int N = questions.size();

        for(int i=0;i<N;++i){
            QuestionRequest questionRequest = quizRequest.getAnswers().get(i);
            Question correspondingQuestion = questionRepository.getOne(questionRequest.getQuestionId());
            int userChoiceId = questionRequest.getChoiceId();
            int questionCorrectId = correspondingQuestion.getCorrectChoiceId();
            questionRequest.setCorrectId(questionCorrectId);
            if(userChoiceId == questionCorrectId){
                score++;
                questionRequest.setTrue(true);
            }else{
                questionRequest.setTrue(false);
            }
        }
        //If the exam is of type: level
        if(quiz.getQuizType().equals("level")){
            int level = score*10/N; //Levels are between 1-10
            quizRequest.setLevel(level);
            Integer languageId = quiz.getLanguageId();
            if(languageId == null){
                //Default is english:
                languageId = 1;
            }

            Language lang = languageRepository.getById(languageId);
            //Insert into member_language
            //Check if the member already exists:
            MemberLanguage memberLanguage = memberLanguageRepository.getByMemberIdAndLanguage(curMember.getId(), lang);
            if(memberLanguage == null){
                memberLanguage = new MemberLanguage(curMember.getId(), lang);
            }
            memberLanguage.setLanguageLevel(level);
            if (level < 3) {
                memberLanguage.setLevelName(LevelName.BEGINNER);
            }
            else if (level < 5) {
                memberLanguage.setLevelName(LevelName.INTERMEDIATE);
            }
            else if (level < 7) {
                memberLanguage.setLevelName(LevelName.UPPER_INTERMEDIATE);
            }
            else {
                memberLanguage.setLevelName(LevelName.ADVANCED);
            }

            memberLanguageRepository.save(memberLanguage);

            if (memberStatusRepository.getByMemberIdAndAndLangId(curMember.getId(), lang.getId()) == null) {
                MemberStatus memberStatus = new MemberStatus();
                memberStatus.setMemberId(curMember.getId());
                memberStatus.setLangId(lang.getId());
                memberStatus.setNumberOfQuestions(0);
                memberStatus.setLevelName(memberLanguage.getLevelName());
                memberStatusRepository.save(memberStatus);
            }
            else {
               MemberStatus memberStatus = memberStatusRepository.getByMemberIdAndAndLangId(curMember.getId(), lang.getId());
                memberStatus.setNumberOfQuestions(0);
                memberStatus.setLevelName(memberLanguage.getLevelName());
                memberStatusRepository.save(memberStatus);
            }
        }
        else {
            if (memberStatusRepository.getByMemberIdAndAndLangId(curMember.getId(), quiz.getLanguageId()) == null) {
                MemberStatus memberStatus = new MemberStatus();
                memberStatus.setMemberId(curMember.getId());
                memberStatus.setLangId(quiz.getLanguageId());
                memberStatus.setNumberOfQuestions(memberStatus.getNumberOfQuestions() + score);
                memberStatus.setLevelName(LevelName.BEGINNER);
                if (memberStatus.getNumberOfQuestions() >= 60) {
                    memberStatus.setLevelName(LevelName.INTERMEDIATE);
                    memberStatus.setNumberOfQuestions(0);
                }
                memberStatusRepository.save(memberStatus);
            }
            else {
                MemberStatus memberStatus = memberStatusRepository.getByMemberIdAndAndLangId(curMember.getId(), quiz.getLanguageId());
                memberStatus.setNumberOfQuestions(memberStatus.getNumberOfQuestions() + score);
                if (memberStatus.getNumberOfQuestions() >= 60) {
                    memberStatus.setNumberOfQuestions(0);
                    if (memberStatus.getLevelName() == LevelName.BEGINNER) {
                        memberStatus.setLevelName(LevelName.INTERMEDIATE);
                    }
                    else if (memberStatus.getLevelName() == LevelName.INTERMEDIATE) {
                        memberStatus.setLevelName(LevelName.UPPER_INTERMEDIATE);
                    }
                    else if (memberStatus.getLevelName() == LevelName.UPPER_INTERMEDIATE) {
                        memberStatus.setLevelName(LevelName.ADVANCED);
                    }
                }
                memberStatusRepository.save(memberStatus);
            }
        }

        quizRequest.setScore(score);

        MemberQuiz memberQuiz = memberQuizRepository.findByMemberIdAndQuizId(curMember.getId(), quizId);
        if(memberQuiz != null){
            memberQuizRepository.deleteById(memberQuiz.getId());
        }

        memberQuizRepository.save(new MemberQuiz(curMember.getId(), quizId, score));

        return  quizRequest;
    }

    public List<QuizDTO>getAllQuizzesByLanguageId(long languageId) {
        List<QuizDTO> quizDTOS = new ArrayList<>();
        quizRepository.getAllByLanguageId(languageId).forEach(quiz ->
                quizDTOS.add(quizDTOConverterService.apply(quiz, null))
        );
        return quizDTOS;
    }

    public List<QuizDTO>getAllQuizzesByLevelId(long levelId) {
        List<QuizDTO> quizDTOS = new ArrayList<>();
        quizRepository.getAllByLevel(levelId).forEach(quiz ->
                quizDTOS.add(quizDTOConverterService.apply(quiz, null))
        );
        return quizDTOS;
    }

    public List<QuizDTO>getAllQuizzesByLevelandLanguageId(int level, int languageId) {
        List<QuizDTO> quizDTOS = new ArrayList<>();
        quizRepository.getAllByLevelAndLanguageId(level, languageId).forEach(quiz ->
                quizDTOS.add(quizDTOConverterService.apply(quiz, null))
        );
        return quizDTOS;
    }
    public List<QuizDTO>getAllQuizzesByLanguageIdandLevelLess(int level, int languageId) {
        List<QuizDTO> quizDTOS = new ArrayList<>();
        quizRepository.getAllByLanguageIdAndLevelIsLessThanEqual(languageId, level).forEach(quiz ->
                quizDTOS.add(quizDTOConverterService.apply(quiz, null))
        );
        return quizDTOS;
    }
}
