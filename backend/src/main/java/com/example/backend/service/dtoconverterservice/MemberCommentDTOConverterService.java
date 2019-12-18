package com.example.backend.service.dtoconverterservice;

import com.example.backend.model.member.comment.MemberComment;
import com.example.backend.model.member.comment.MemberCommentDTO;

import com.example.backend.service.member.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public
class MemberCommentDTOConverterService {

    @Autowired
    JwtUserDetailsService jwtUserDetailsService;

    public MemberCommentDTO apply(MemberComment memberComment) {
        MemberCommentDTO memberCommentDTO = new MemberCommentDTO();
        memberCommentDTO.setId(memberComment.getId());
        memberCommentDTO.setMemberId(memberComment.getMemberId());
        memberCommentDTO.setMemberName(jwtUserDetailsService.getUsernameById(memberComment.getMemberId()));
        memberCommentDTO.setCommentatorId(memberComment.getCommentatorId());
        memberCommentDTO.setCommentatorName(jwtUserDetailsService.getUsernameById(memberComment.getCommentatorId()));
        memberCommentDTO.setCreatedAt(memberComment.getCreatedAt());
        memberCommentDTO.setUpdatedAt(memberComment.getUpdatedAt());
        memberCommentDTO.setComment(memberComment.getComment());
        return memberCommentDTO;
    }

    public List<MemberCommentDTO> applyAll(List<MemberComment> memberComments){
        List<MemberCommentDTO> list = new ArrayList<>();

        memberComments.forEach(memberComment -> {
            list.add(apply(memberComment));
        });

        return list;
    }
}
