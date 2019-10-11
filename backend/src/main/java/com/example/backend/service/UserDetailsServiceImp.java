package com.example.backend.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Member member = memberRepository.findByName(uname);
        if(member==null){
            throw new UsernameNotFoundException("Username is Wrong");
        }
        CustomUserDetails memberDetails = new CustomUserDetails(member);
        return memberDetails;
    }
}
