package com.example.backend.controller.member;

import com.example.backend.Util.JwtUserDetailsServiceUtil;
import com.example.backend.config.JwtTokenUtil;
import com.example.backend.model.member.JwtResponse;
import com.example.backend.model.member.MemberDTO;
import com.example.backend.service.member.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/member")
public class MemberController {

    @Autowired
    JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @GetMapping("/profile")
    public ResponseEntity<?> getUser(){
        String username = jwtUserDetailsService.getUsername();

        return ResponseEntity.ok(jwtUserDetailsService.getByUsername(username));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody MemberDTO memberDTO, HttpServletRequest request){

        String username = jwtUserDetailsService.getUsername();

        JwtUserDetailsServiceUtil serviceOutput = jwtUserDetailsService.updateMember(memberDTO, username);

        if(!serviceOutput.isValid()){ //If the request is invalid return the error message
            return  ResponseEntity.ok(serviceOutput.getInfo());
        }
        final UserDetails userDetails = jwtUserDetailsService
                .loadUserByUsername(serviceOutput.getMember().getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);


        return ResponseEntity.ok(new JwtResponse(token));

    }

    @PostMapping("/addlang")
    public ResponseEntity<?> addLanguage(@RequestBody List<String> languages){
        return  ResponseEntity.ok(jwtUserDetailsService.addLanguage(languages));
    }

}
