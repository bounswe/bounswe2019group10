package com.example.backend.service.comment;

import com.example.backend.model.member.comment.MemberComment;
import com.example.backend.model.member.comment.MemberCommentDTO;
import com.example.backend.model.member.comment.MemberCommentMakeDTO;
import com.example.backend.model.member.comment.MemberRating;
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
        memberComment.setRating(memberCommentDTO.getRating());

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


    public MemberRating getMyRating(){
        int memberId = jwtUserDetailsService.getUserId();

        List<MemberComment> comments = memberCommentRepository.getAllByMemberId(memberId);

        MemberRating memberRating = new MemberRating();

        memberRating.setNumberOfRatings(comments.size());
        if(memberRating.getNumberOfRatings() == 0){
            memberRating.setRating(0);
            return memberRating;
        }
        double rating = comments.stream().mapToDouble(MemberComment::getRating).sum();

        memberRating.setRating(1.0*rating/memberRating.getNumberOfRatings());

        return memberRating;
    }

    public MemberRating getRating(int memberId){

        List<MemberComment> comments = memberCommentRepository.getAllByMemberId(memberId);

        MemberRating memberRating = new MemberRating();

        memberRating.setNumberOfRatings(comments.size());
        if(memberRating.getNumberOfRatings() == 0){
            memberRating.setRating(0);
            return memberRating;
        }

        double rating = comments.stream().mapToDouble(MemberComment::getRating).sum();

        memberRating.setRating(1.0*rating/memberRating.getNumberOfRatings());

        return memberRating;
    }


}
