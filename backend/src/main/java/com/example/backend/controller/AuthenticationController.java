package com.example.backend.controller;

import com.example.backend.domain.Member;
import com.example.backend.service.AuthenticationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login-page")
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/login")
    public String getMember(@RequestParam(value="nickname") String name, @RequestParam(value="password") String pass){
        Member member = new Member(name, pass);
        return authenticationService.login(member);
    }

    @PostMapping("/sign-up")
    public String signUp(@RequestParam(value="nickname") String name, @RequestParam(value="password") String pass, @RequestParam(value="email") String email){
        Member member = new Member(name, pass, email);
        System.out.println(member.getName());
        return authenticationService.signUp(member);
    }





}
