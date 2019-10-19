package com.example.backend.controller.member;

import com.example.backend.config.JwtTokenUtil;
import com.example.backend.model.member.JwtRequest;
import com.example.backend.model.member.JwtResponse;
import com.example.backend.model.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberControllerTest {

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    JwtAuthenticationController jwtAuthenticationController;

    @Autowired
    MemberController memberController;

    @DisplayName("User Profile testing")
    @Test
    void testGetUserInfo() throws Exception {

        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUsername("username1");
        jwtRequest.setPassword("password1");
        ResponseEntity<?> response = jwtAuthenticationController.createAuthenticationToken(jwtRequest);
        JwtResponse bearer = (JwtResponse) response.getBody();

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + bearer.getToken());

        Member member = (Member) memberController.getUser(request).getBody();

        assertEquals("username1", member.getUsername());
    }
}