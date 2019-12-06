package com.example.backend.service.comment;

import com.example.backend.model.member.MemberComment;
import com.example.backend.model.member.MemberCommentDTO;
import com.example.backend.repository.member.MemberCommentRepository;
import com.example.backend.service.member.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;


@Service
public class CommentService {

    @Autowired
    MemberCommentRepository memberCommentRepository;

    @Autowired
    JwtUserDetailsService jwtUserDetailsService;

    public List<MemberComment> getAllComments() {
        int memberId = jwtUserDetailsService.getUserId();

        return memberCommentRepository.getAllByMemberId(memberId);
    }

    public MemberComment makeComment(MemberCommentDTO memberCommentDTO) {
        MemberComment memberComment = new MemberComment();

        memberComment.setMemberId(memberCommentDTO.getMemberId());
        memberComment.setCommentatorId(jwtUserDetailsService.getUserId());
        memberComment.setComment(memberCommentDTO.getComment());
        LocalDateTime localDateTime = LocalDateTime.now();
        memberComment.setCreatedAt(Timestamp.valueOf(localDateTime));

        memberCommentRepository.save(memberComment);

        return memberCommentRepository.getOne(memberComment.getId());
    }


    public String deleteComment(int id) {

        MemberComment memberComment = memberCommentRepository.findById(id).orElse(null);

        if(memberComment == null){
            return "No Such Comment Exists!";
        }

        int memberId = jwtUserDetailsService.getUserId();

        if (memberId == memberComment.getCommentatorId()) {
            memberCommentRepository.deleteById(id);
            return "Comment Deleted Successfully!";
        }

        return "This is not your Comment!";
    }

}
