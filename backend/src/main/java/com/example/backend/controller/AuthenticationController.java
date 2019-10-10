package com.example.backend.controller;

import com.example.backend.domain.Member;
import com.example.backend.repository.MemberRepository;
import com.example.backend.service.AuthenticationService;
import com.example.backend.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login-page")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private MemberRepository memberRepository;


    @PostMapping("/login")
    public String getMember(@RequestParam(value="username") String username, @RequestParam(value="password") String pass){
        Member member = memberRepository.findByUsernameAndPassword(username, pass);
        return authenticationService.login(member);
    }

    @PostMapping("/sign-up")
    public String signUp(@RequestParam(value="nickname") String name, @RequestParam(value="password") String pass, @RequestParam(value="email") String email){
        //Member member = new Member(name, pass, email);
        //System.out.println(member.getUsername());
        System.out.println("**************************************************");
        System.out.println("**************************************************");
        System.out.println("**************************************************");
        System.out.println("**************************************************");
        System.out.println("**************************************************");
        System.out.println("**************************************************");


        return authenticationService.signUp(name, pass, email);
    }





}
