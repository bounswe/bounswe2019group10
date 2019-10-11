package com.example.backend.security;

import com.example.backend.domain.Member;
import com.example.backend.repository.MemberRepository;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class CustomUserDetails extends Member implements UserDetails {
    @Id
    public Long detId;
    private String username;
    private String password;
    private boolean isExpert;
    private String role;
    private String mail;
    private String name;
    private Collection<? extends GrantedAuthority>  auths;


    public CustomUserDetails(final Member member){
        this.detId = member.getId();
        this.username = member.getUsername();
        this.password = member.getPassword();
        this.isExpert = member.isExpert();
        this.role = member.getRole();
        this.mail = member.getMail();
        this.name = member.getName();

        List<GrantedAuthority> memberAuthList = new ArrayList<>();
        SimpleGrantedAuthority memberAuth = new SimpleGrantedAuthority(member.getRole());
        memberAuthList.add(memberAuth);
        auths = (Collection<? extends GrantedAuthority> ) memberAuthList;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return auths;
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}