package com.example.backend.service.member;

import com.example.backend.Util.JwtUserDetailsServiceUtil;
import com.example.backend.model.language.Language;
import com.example.backend.model.member.Member;
import com.example.backend.model.member.MemberDTO;
import com.example.backend.model.member.MemberLanguage;
import com.example.backend.repository.language.LanguageRepository;
import com.example.backend.repository.member.MemberLanguageRepository;
import com.example.backend.repository.member.MemberRepository;
import com.example.backend.service.dtoconverterservice.MemberDTOConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private MemberLanguageRepository memberLanguageRepository;

    @Autowired
    private MemberDTOConverterService memberDTOConverterService;

    @Autowired
    private PasswordEncoder bcryptEncoder;


    private final String mailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$";
    private final String usernameRegex = "^[A-Za-z0-9_-]{4,16}$";
    private final String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
    private final String emailWarning = "The email is invalid.";
    private final String usernameWarning = "The username must contain at least 4 and at most 16 characters. " +
            "Username can only include alphanumeric characters and \"-\" or \"_\"";
    private final String passwordWarning = "The password should be at least 8 characters long. " +
            "The password should contain at least one letter and at least one digit.";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member user = memberRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        List<GrantedAuthority> memberAuthList = new ArrayList<>();
        SimpleGrantedAuthority memberAuth = new SimpleGrantedAuthority(user.getRole());
        memberAuthList.add(memberAuth);
        return new User(user.getUsername(), user.getPassword(),
                (Collection<? extends GrantedAuthority>) memberAuthList);
    }

    public Member getByUsername(String name) {
        return memberRepository.findByUsername(name);
    }

    public Member getByMail(String mail){return memberRepository.findByMail(mail);}

    public String getUsername(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return userDetails.getUsername();
    }

    public JwtUserDetailsServiceUtil save(MemberDTO user) {
        Member newUser = new Member();

        String username = user.getUsername();
        Pattern pattern = Pattern.compile(usernameRegex);
        //Check format
        if(!pattern.matcher(username).matches()){
            return new JwtUserDetailsServiceUtil(false, null, usernameWarning);
        }
        //Check for existent username
        Member m = memberRepository.findByUsername(username);
        if(m!=null)
            return new JwtUserDetailsServiceUtil(false, null, "The username already exists.");
        newUser.setUsername(username);


        String mail = user.getMail();
        pattern = Pattern.compile(mailRegex);
        if(!pattern.matcher(mail).matches()){
            return new JwtUserDetailsServiceUtil(false, null, emailWarning);
        }
        newUser.setMail(mail);

        String password = user.getPassword();
        pattern = Pattern.compile(passwordRegex);
        if(!pattern.matcher(password).matches()){
            return new JwtUserDetailsServiceUtil(false, null, passwordWarning);
        }
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));

        newUser.setRole("USER");

        return new JwtUserDetailsServiceUtil(true, memberRepository.save(newUser), "Register is successfull.");
    }

    public JwtUserDetailsServiceUtil updateMember(MemberDTO memberDTO, String memberUname){
        Member member = memberRepository.findByUsername(memberUname);

        member.setName(memberDTO.getName());
        member.setSurname(memberDTO.getSurname());

        String username = memberDTO.getUsername();
        Pattern pattern = Pattern.compile(usernameRegex);
        //Check format
        if(!pattern.matcher(username).matches()){
            return new JwtUserDetailsServiceUtil(false, null, usernameWarning);
        }
        //Check for existent username
        if(!memberUname.equals(username)){ //If the username is updated as well
            Member m = memberRepository.findByUsername(username);
            if(m!=null)
                return new JwtUserDetailsServiceUtil(false, null, "The username already exists.");
            member.setUsername(username);
        }

        String mail = memberDTO.getMail();
        pattern = Pattern.compile(mailRegex);
        if(!pattern.matcher(mail).matches()){
            return new JwtUserDetailsServiceUtil(false, null, emailWarning);
        }
        member.setMail(mail);

        String password = memberDTO.getPassword();
        pattern = Pattern.compile(passwordRegex);
        if(!pattern.matcher(password).matches()){
            return new JwtUserDetailsServiceUtil(false, null, passwordWarning);
        }
        member.setPassword(bcryptEncoder.encode(password));
        return new JwtUserDetailsServiceUtil(true, memberRepository.save(member), "Update is successful.");
    }

    public MemberDTO addLanguage(List<String> languages){
        Member member = memberRepository.findByUsername(getUsername());
        languages.forEach(language -> {
            Language lang = languageRepository.getByLanguageName(language);
            MemberLanguage memLang = new MemberLanguage();
            memLang.setLanguage(lang);
            memLang.setMemberId(member.getId());
            memberLanguageRepository.save(memLang);
        });
        return memberDTOConverterService.apply(member);
    }
}