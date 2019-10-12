package com.example.backend.service;

import com.example.backend.config.JwtTokenUtil;
import com.example.backend.model.Member;
import com.example.backend.model.MemberDTO;
import com.example.backend.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member user = memberRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new User(user.getUsername(), ((Member) user).getPassword(),
                new ArrayList<>());
    }

    public Member getByName(String name) {
        return memberRepository.findByUsername(name);
    }


    public Member save(MemberDTO user) {
        Member newUser = new Member();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        newUser.setRole("USER");
        return memberRepository.save(newUser);
    }

    public String login(Member member){
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(member.getUsername(), member.getPassword()));
        String sessionToken = jwtTokenUtil.generateToken(member);
        SecurityContextHolder.getContext().setAuthentication(auth);
        return "Bearer " + sessionToken;


    }

}