package com.example.backend.Util;

import com.example.backend.model.member.Member;

public class JwtUserDetailsServiceUtil {
    boolean valid;
    Member member;
    String info;

    public JwtUserDetailsServiceUtil(boolean valid, Member member, String info) {
        this.valid = valid;
        this.member = member;
        this.info = info;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
