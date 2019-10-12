package com.example.backend.controller;

import com.example.backend.domain.Member;
import com.example.backend.dto.MemberDTO;
import com.example.backend.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/user/nickname/{username}")
    public Member /*MemberDTO*/ getUser(@PathVariable String username){
        return memberService.getMember(username);
    }



    @PostMapping("/create")
    public Member /*MemberDTO*/ createMember(@RequestParam(value="nickname") String username, @RequestParam(value="password") String password){
        return memberService.createAccount(username, passwordEncoder.encode(password));
    }

    @DeleteMapping("/delete")
    public void deleteMember(@RequestParam(value="id") int id){
       memberService.deleteMember(id);
    }



}