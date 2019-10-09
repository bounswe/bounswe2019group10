package com.example.backend.service;

import com.example.backend.domain.Member;
import com.example.backend.repository.MemberRepository;
import com.example.backend.security.JwtAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.ResponseEntity;

public class AuthenticationService {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;
    @Autowired
    private AuthenticationManager authenticationManager;

    public String signUp(Member member) {
        try {
            if (memberRepository.findByName(member.getName()) != null ||
                    memberRepository.findByMail(member.getMail()) != null) {
                //username already exists
                throw new Exception("User already exists");
            }
        }catch (Exception e){System.out.println(e);}

        member.setPassword(passwordEncoder.encode(member.getPassword()));
        memberRepository.save(member);
        return "Sign up success!";
    }

    public String login(Member member){
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(member.getName(), member.getPassword()));
        String sessionToken = jwtAuthenticationProvider.generateToken(member);
        SecurityContextHolder.getContext().setAuthentication(auth);
        return "Bearer " + sessionToken;


    }

}
