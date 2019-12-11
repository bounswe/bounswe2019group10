package com.example.backend.controller.message;


import com.example.backend.config.JwtTokenUtil;
import com.example.backend.model.message.ConversationDTO;
import com.example.backend.model.message.MessageDTO;
import com.example.backend.model.message.MessageRequest;
import com.example.backend.service.member.JwtUserDetailsService;
import com.example.backend.service.message.MessageService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;



    @GetMapping("/{conversationId}")
    @ApiOperation(value = "Get conversation by ID.")
    public ResponseEntity<ConversationDTO> getById(@PathVariable int conversationId) {
        String memberUname = jwtUserDetailsService.getUsername();
        return ResponseEntity.ok(messageService.getById(conversationId, memberUname));
    }

    @PostMapping("/send")
    @ApiOperation(value = "Send a message to another user.")
    public ResponseEntity<MessageDTO> sendMessage(@RequestBody MessageRequest messageRequest){
        String memberUsername = jwtUserDetailsService.getUsername();
        return ResponseEntity.ok(messageService.addMessage(messageRequest, memberUsername));
    }

    @GetMapping("/conversations")
    @ApiOperation(value = "Get all of the conversations of the user.")
    public ResponseEntity<List<ConversationDTO>> getConversations() {
        String memberUname = jwtUserDetailsService.getUsername();
        return ResponseEntity.ok(messageService.getAllConversations(memberUname));
    }

}
