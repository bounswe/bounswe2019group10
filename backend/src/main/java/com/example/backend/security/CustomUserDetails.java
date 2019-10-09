package com.example.backend.security;

import com.example.backend.domain.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails extends Member implements UserDetails {
    private Collection<? extends GrantedAuthority>  auths;

    public CustomUserDetails(final Member member){
        super(member);
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
    public String getUsername() {
        return super.getName();
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