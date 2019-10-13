package com.example.backend.service;

import com.example.backend.config.JwtTokenUtil;
import com.example.backend.model.Member;
import com.example.backend.model.MemberDTO;
import com.example.backend.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member user = memberRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        List<GrantedAuthority> memberAuthList = new ArrayList<>();
        SimpleGrantedAuthority memberAuth = new SimpleGrantedAuthority(user.getRole());
        memberAuthList.add(memberAuth);
        User newUser = new User(user.getUsername(), user.getPassword(),
                (Collection<? extends GrantedAuthority>) memberAuthList);
        return newUser;
    }

    public Member getByUsername(String name) {
        return memberRepository.findByUsername(name);
    }


    public Member save(MemberDTO user) {
        Member newUser = new Member();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        newUser.setMail(user.getMail());
        newUser.setName(user.getName());
        newUser.setSurname(user.getSurname());
        newUser.setRole("USER");
        return memberRepository.save(newUser);
    }


}