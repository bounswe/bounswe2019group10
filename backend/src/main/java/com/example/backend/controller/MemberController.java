package com.example.backend.controller;

import com.example.backend.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/member")
public class MemberController {


    @Autowired
    JwtUserDetailsService jwtUserDetailsService;

    @RequestMapping("/get")
    public ResponseEntity<?> saveUser(@RequestParam(value="username") String name) throws Exception {
        return ResponseEntity.ok(jwtUserDetailsService.getByName(name));
    }

}
