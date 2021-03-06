package com.example.backend.controller.member;

import com.example.backend.config.JwtTokenUtil;
import com.example.backend.model.member.JwtRequest;
import com.example.backend.model.member.JwtResponse;
import com.example.backend.model.member.Member;
import com.example.backend.model.member.MemberDTO;
import com.example.backend.repository.member.MemberRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtAuthenticationControllerTest {
/*
    @Autowired
    JwtAuthenticationController jwtAuthenticationController;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @DisplayName("Authentication with username and password testing")
    @Test
    void testAuthenticationUsernamePassword() throws Exception {
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUsername("username1");
        jwtRequest.setPassword("password1");
        ResponseEntity<?> response = jwtAuthenticationController.createAuthenticationToken(jwtRequest);
        JwtResponse bearer = (JwtResponse) response.getBody();
        String username = jwtTokenUtil.getUsernameFromToken(bearer.getToken());
        assertEquals("username1", username);
    }

    @DisplayName("Authentication with mail and password testing")
    @Test
    void testAuthenticationMailPassword() throws Exception {
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUsername("9@9.com");
        jwtRequest.setPassword("password9");
        ResponseEntity<?> response = jwtAuthenticationController.createAuthenticationToken(jwtRequest);
        JwtResponse bearer = (JwtResponse) response.getBody();
        String username = jwtTokenUtil.getUsernameFromToken(bearer.getToken());
        assertEquals("username9", username);
    }

    @DisplayName("Save user testing")
    @Test
    void testAuthenticationSaveUser() throws Exception {
        MemberDTO dto = new MemberDTO();
        dto.setUsername("usernameTest");
        dto.setPassword("passwordTest");
        dto.setMail("test@test.com");

        jwtAuthenticationController.saveUser(dto);

        Member member = memberRepository.findByUsername("usernameTest");

        memberRepository.deleteById(member.getId());

        assertEquals("usernameTest", member.getUsername());
    }
*/
}
