package com.example.backend.service.quiz;

import com.example.backend.model.quiz.*;
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
    private QuestionRepository questionRepository;


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

    public QuizRequest evaluateQuiz(QuizRequest quizRequest){
        //Take the request, add the nececssary fields and send back
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
        }

        quizRequest.setScore(score);

        //TODO ADD THE DETAILS OF THE QUIZ TO THE NEW TABLE

        return  quizRequest;
    }


}
