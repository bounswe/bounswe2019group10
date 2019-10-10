package com.example.backend.controller;

import com.example.backend.domain.Member;
import com.example.backend.dto.MemberDTO;
import com.example.backend.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    MemberService memberService;

    @GetMapping("/user/nickname/{username}")
    public MemberDTO getUser(@PathVariable String username){
        return memberService.findMemberByUserName(username);
    }


    @PostMapping("/create")
    public MemberDTO createMember(@RequestParam(value="nickname") String username, @RequestParam(value="password") String password){
       return memberService.createAccount(username, password);
    }

    @DeleteMapping("/delete")
    public void deleteMember(@RequestParam(value="id") int id){
       memberService.deleteMember(id);
    }



}