package com.example.backend.controller.comment;

import com.example.backend.model.member.MemberComment;
import com.example.backend.model.member.MemberCommentDTO;
import com.example.backend.service.comment.CommentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;


    @GetMapping()
    @ApiOperation(value = "Get comments made to me")
    public ResponseEntity<List<MemberComment>> getComments(){
        return ResponseEntity.ok(commentService.getAllComments());
    }

    @PostMapping("/make")
    @ApiOperation(value = "Make comment to a user")
    public ResponseEntity<MemberComment> makeComment(@RequestBody MemberCommentDTO memberCommentDTO){
        return ResponseEntity.ok(commentService.makeComment(memberCommentDTO));
    }

    @PostMapping("/delete")
    @ApiOperation(value = "delete comment")
    public ResponseEntity<String> deleteComment(@RequestParam(value = "id") int id){
        return ResponseEntity.ok(commentService.deleteComment(id));
    }
}
