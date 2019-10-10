package com.example.backend.service;


import com.example.backend.DTOConverterService.MemberDTOConverterService;
import com.example.backend.domain.Member;
import com.example.backend.dto.MemberDTO;
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

    @Autowired
    private MemberDTOConverterService memberDTOConverterService;

    public MemberDTO findMemberByUserName(String username) {
        return memberDTOConverterService.apply(memberRepository.findByUsername(username));
    }

    public MemberDTO createAccount(String username, String password) {
        Member member = new Member(null, null, password, username, null, null, null);
        return memberDTOConverterService.apply(memberRepository.save(member));
    }

    public void deleteMember(int id) {
        memberRepository.deleteById(id);
    }


    @Override
    public  UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username);
        if(member==null){
            throw new UsernameNotFoundException("Username is Wrong");
        }
        CustomUserDetails memberDetails = new CustomUserDetails(member);
        return memberDetails;
    }
}


/*
*
Not relevant
#spring.datasource.url=localhost:5432
#spring.datasource.username=postgres
#spring.datasource.password=password

* */
