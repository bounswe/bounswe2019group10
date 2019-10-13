package com.example.backend.controller;

import com.example.backend.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/member")
public class MemberController {

    @Autowired
    JwtUserDetailsService jwtUserDetailsService;

    @GetMapping("/get")
    public ResponseEntity<?> getUser(@RequestParam(value="username") String username){
        return ResponseEntity.ok(jwtUserDetailsService.getByUsername(username));
    }

}
