package com.example.backend.controller;

import com.example.backend.domain.Member;
import com.example.backend.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    MemberService memberService;

    @GetMapping("/user")
    public String getUser(){
        return "user";
    }


    @PostMapping("/create")
    public String createMember(@RequestParam(value="nickname") String name, @RequestParam(value="password") String pass){
        memberService.createAccount(name, pass);
        return "done";
    }

    @DeleteMapping("/delete")
    public String deleteMember(@RequestParam(value="id") int id){
       memberService.deleteMember(id);
       return "done";
    }



}