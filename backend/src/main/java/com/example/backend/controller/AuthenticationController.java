package com.example.backend.controller;

import com.example.backend.domain.Member;
import com.example.backend.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    MemberService memberService;

    @GetMapping("/login")
    public String getMember(@RequestParam(value="nickname") String name, @RequestParam(value="password") String pass){
        Member member = memberService.getMember(name, pass);
        return member.getName() + " " + member.getPassword();
    }

    @PostMapping("/sign-up")
    public String getMember(@RequestParam(value="nickname") String name, @RequestParam(value="password") String pass,
                            @RequestParam("email") String email){
        Member member = memberService.createMember(name, pass, email, "", false);
        return "Created account";
    }





}
