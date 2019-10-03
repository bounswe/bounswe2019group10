package com.example.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {
    @GetMapping("/user")
    public String getUser(){
        return "user";
    }
    @GetMapping("/login")
    public String getUser(@RequestParam(value="nickname") String name, @RequestParam(value="password") String pass){
        return name + " " + pass;
    }
}