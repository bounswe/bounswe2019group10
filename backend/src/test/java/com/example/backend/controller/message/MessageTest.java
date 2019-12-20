package com.example.backend.controller.message;

import com.example.backend.config.JwtTokenUtil;
import com.example.backend.controller.member.JwtAuthenticationController;
import com.example.backend.controller.member.MemberController;
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
public class MessageTest {
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    JwtAuthenticationController jwtAuthenticationController;

    @Autowired
    MemberController memberController;

    @Autowired
    WritingController writingController;

}
