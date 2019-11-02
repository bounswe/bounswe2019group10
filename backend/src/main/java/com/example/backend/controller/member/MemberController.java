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

@RestController
@CrossOrigin
@RequestMapping("/member")
public class MemberController {

    @Autowired
    JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @GetMapping("/profile")
    public ResponseEntity<?> getUser(HttpServletRequest request){
        final String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        // JWT Token is in the form "Bearer token". Remove Bearer word and get
        // only the Token
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
        }
        return ResponseEntity.ok(jwtUserDetailsService.getByUsername(jwtTokenUtil.getUsernameFromToken(jwtToken)));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody MemberDTO memberDTO, HttpServletRequest request){
        final String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        // JWT Token is in the form "Bearer token". Remove Bearer word and get
        // only the Token
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
        }
        String memberUname = jwtTokenUtil.getUsernameFromToken(jwtToken);
        JwtUserDetailsServiceUtil serviceOutput = jwtUserDetailsService.updateMember(memberDTO, memberUname);

        if(!serviceOutput.isValid()){ //If the request is invalid return the error message
            return  ResponseEntity.ok(serviceOutput.getInfo());
        }
        final UserDetails userDetails = jwtUserDetailsService
                .loadUserByUsername(serviceOutput.getMember().getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);


        return ResponseEntity.ok(new JwtResponse(token));

    }

}
