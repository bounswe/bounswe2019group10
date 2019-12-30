package com.example.backend.repository.member;

import com.example.backend.model.member.comment.MemberComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberCommentRepository extends JpaRepository<MemberComment, Integer> {

    List<MemberComment> getAllByMemberId(int memberId);

}
