package com.example.backend.repository.member;

import com.example.backend.model.member.MemberQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberQuizRepository extends JpaRepository<MemberQuiz, Integer> {

    MemberQuiz findByMemberIdAndQuizId(int memberId, int quizId);
}
