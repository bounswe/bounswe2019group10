package com.example.backend.controller.writing;

import com.example.backend.config.JwtTokenUtil;
import com.example.backend.controller.writing.WritingController;
import com.example.backend.model.member.JwtRequest;
import com.example.backend.model.member.JwtResponse;
import com.example.backend.model.writing.WritingResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class WritingTest {
/*
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    JwtAuthenticationController jwtAuthenticationController;

    @Autowired
    MemberController memberController;

    @Autowired
    WritingController writingController;

    @DisplayName("Writing Get Testing")
    @Test
    void testWritingGet() throws Exception {

        String token = initSession();

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + token);
        WritingResponse writingResponse = (WritingResponse) writingController.getById(1).getBody();
        assertEquals(1, writingResponse.getWritingDTO().getId());
    }


    public String initSession() throws Exception{
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUsername("username1");
        jwtRequest.setPassword("password1");
        ResponseEntity<?> response = jwtAuthenticationController.createAuthenticationToken(jwtRequest);
        JwtResponse bearer = (JwtResponse) response.getBody();
        return bearer.getToken();
    }
    */
}
