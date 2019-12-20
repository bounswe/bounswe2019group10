package com.example.backend.controller.message;

import com.example.backend.config.JwtTokenUtil;
import com.example.backend.controller.member.JwtAuthenticationController;
import com.example.backend.controller.member.MemberController;
import com.example.backend.controller.writing.WritingController;
import com.example.backend.model.member.JwtRequest;
import com.example.backend.model.member.JwtResponse;
import com.example.backend.model.message.ConversationDTO;
import com.example.backend.model.message.MessageDTO;
import com.example.backend.model.message.MessageRequest;
import com.example.backend.model.writing.WritingResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MessageTest {
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    JwtAuthenticationController jwtAuthenticationController;

    @Autowired
    MemberController memberController;

    @Autowired
    MessageController messageController;

    @DisplayName("Message Send Test")
    @Test
    void testMessageSent() throws Exception {

        String token = initSession();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + token);
        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setMessage("Hello This is a Test message!!!");
        messageRequest.setTargetUsername("selim123");
        MessageDTO messageDTO = messageController.sendMessage(messageRequest).getBody();
        int messageId = messageDTO.getId();
        int conversationId = messageDTO.getConversationId();
        ConversationDTO conversationDTO = messageController.getById(conversationId).getBody();
        assertTrue(conversationDTO.getMessages().stream().anyMatch(m -> m.getId() == messageId));
    }

    public String initSession() throws Exception{
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUsername("username1");
        jwtRequest.setPassword("password1");
        ResponseEntity<?> response = jwtAuthenticationController.createAuthenticationToken(jwtRequest);
        JwtResponse bearer = (JwtResponse) response.getBody();
        return bearer.getToken();
    }

}
