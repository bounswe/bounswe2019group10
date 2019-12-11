package com.example.backend.service.comment;

import com.example.backend.model.member.MemberComment;
import com.example.backend.model.member.MemberCommentDTO;
import com.example.backend.model.member.MemberCommentMakeDTO;
import com.example.backend.repository.member.MemberCommentRepository;
import com.example.backend.service.dtoconverterservice.MemberCommentDTOConverterService;
import com.example.backend.service.member.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class CommentService {

    @Autowired
    MemberCommentRepository memberCommentRepository;

    @Autowired
    JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    MemberCommentDTOConverterService memberCommentDTOConverterService;
    public List<MemberCommentDTO> getAllMyComments() {
        int memberId = jwtUserDetailsService.getUserId();

        return memberCommentDTOConverterService.applyAll(memberCommentRepository.getAllByMemberId(memberId));

    }

    public List<MemberCommentDTO> getAllComments(int memberId) {

        return memberCommentDTOConverterService.applyAll(memberCommentRepository.getAllByMemberId(memberId));
    }

    public MemberComment makeComment(MemberCommentMakeDTO memberCommentDTO) {
        MemberComment memberComment = new MemberComment();

        memberComment.setMemberId(memberCommentDTO.getMemberId());
        memberComment.setCommentatorId(jwtUserDetailsService.getUserId());
        memberComment.setComment(memberCommentDTO.getComment());
        LocalDateTime localDateTime = LocalDateTime.now();
        memberComment.setCreatedAt(Timestamp.valueOf(localDateTime));
        memberComment.setUpdatedAt(Timestamp.valueOf(localDateTime));

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

    public MemberComment updateComment(MemberCommentDTO memberCommentDTO) {

        MemberComment memberComment = memberCommentRepository.findById(memberCommentDTO.getId()).orElse(null);

        if (memberComment == null || jwtUserDetailsService.getUserId() != memberComment.getCommentatorId()){
            return null;
        }

        memberComment.setComment(memberCommentDTO.getComment());
        LocalDateTime localDateTime = LocalDateTime.now();
        memberComment.setUpdatedAt(Timestamp.valueOf(localDateTime));

        memberCommentRepository.save(memberComment);

        return memberCommentRepository.getOne(memberComment.getId());
    }

}
