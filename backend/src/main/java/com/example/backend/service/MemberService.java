package com.example.backend.service;


import com.example.backend.domain.Member;
import com.example.backend.repository.MemberRepository;
import com.example.backend.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class MemberService  implements UserDetailsService {
    @Autowired
    private MemberRepository memberRepository;

    public Member getMember(int id) {
        return memberRepository.getOne(id);
    }

    public Member getMember(String uname) {
        return memberRepository.findByName(uname);
    }

    public Member getMember(String name, String pass) {
        return memberRepository.getByNameAndPassword(name, pass);
    }

    public Member createMember(String nickname, String password, String mail, String bio, Boolean isExpert, String role) {
        Member member = new Member(nickname, password, mail, bio, isExpert, role);
        return memberRepository.save(member);
    }

    public Member createAccount(String nickname, String password) {
        Member member = new Member(nickname, password);
        return memberRepository.save(member);
    }

    public void deleteMember(int id) {
        memberRepository.deleteById(id);
    }


    @Override
    public  UserDetails loadUserByUsername(String uname) throws UsernameNotFoundException {
        Member member = memberRepository.findByName(uname);
        if(member==null){
            throw new UsernameNotFoundException("Username is Wrong");
        }
        CustomUserDetails memberDetails = new CustomUserDetails(member);
        return memberDetails;
    }
}

