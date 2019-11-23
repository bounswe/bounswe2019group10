package com.example.backend.service.dtoconverterservice;

import com.example.backend.model.member.MemberQuiz;
import com.example.backend.model.quiz.QuizDTO;
import com.example.backend.model.quiz.QuizResponseDTO;
import com.example.backend.repository.member.MemberQuizRepository;
import com.example.backend.service.member.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuizResponseDTOConverterService {

    @Autowired
    JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    MemberQuizRepository memberQuizRepository;

    public QuizResponseDTO apply(QuizDTO quiz){

        MemberQuiz memberQuiz = memberQuizRepository
                .findByMemberIdAndQuizId(jwtUserDetailsService.getUserId(), quiz.getId());

        if(memberQuiz != null){
            return new QuizResponseDTO(quiz, memberQuiz.getScore(), true);
        }else{
            return new QuizResponseDTO(quiz, 0, false);
        }

    }

    public List<QuizResponseDTO> applyAll(List<QuizDTO> quizzes){
        List<QuizResponseDTO> list = new ArrayList<>();
        quizzes.forEach(quiz -> {
            list.add(apply(quiz));
        });
        return list;
    }

}
