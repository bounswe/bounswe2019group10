package com.example.backend.controller.member;

import com.example.backend.Util.JwtUserDetailsServiceUtil;
import com.example.backend.config.JwtTokenUtil;
import com.example.backend.model.member.JwtResponse;
import com.example.backend.model.member.Member;
import com.example.backend.model.member.MemberDTO;
import com.example.backend.model.member.MemberLanguage;
import com.example.backend.service.aws.AmazonClient;
import com.example.backend.service.member.JwtUserDetailsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/member")
public class MemberController {

    @Autowired
    JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AmazonClient amazonClient;

    @GetMapping("/profile")
    @ApiOperation(value = "Get profile information")
    public ResponseEntity<Member> getUser(){
        String username = jwtUserDetailsService.getUsername();

        return ResponseEntity.ok(jwtUserDetailsService.getByUsername(username));
    }

    @GetMapping("/{memberId}")
    @ApiOperation(value = "Get profile information by member id")
    public ResponseEntity<Member> getUserbyId(@PathVariable int memberId){

        return ResponseEntity.ok(jwtUserDetailsService.getbyUserId(memberId));
    }

    @PutMapping("/update")
    @ApiOperation(value = "Update profile information")
    public ResponseEntity<JwtResponse> updateUser(@RequestBody MemberDTO memberDTO){

        String username = jwtUserDetailsService.getUsername();

        JwtUserDetailsServiceUtil serviceOutput = jwtUserDetailsService.updateMember(memberDTO, username);

        if(!serviceOutput.isValid()){ //If the request is invalid return the error message
            return  ResponseEntity.ok(new JwtResponse(serviceOutput.getInfo()));
        }
        final UserDetails userDetails = jwtUserDetailsService
                .loadUserByUsername(serviceOutput.getMember().getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);


        return ResponseEntity.ok(new JwtResponse(token));

    }

    @PostMapping("/addlang")
    @ApiOperation(value = "Add a new language")
    public ResponseEntity<List<MemberLanguage>> addLanguage(@RequestBody List<String> languages){
        return  ResponseEntity.ok(jwtUserDetailsService.addLanguage(languages));
    }

    @PostMapping("/removelang")
    @ApiOperation(value = "remove selected language")
    public ResponseEntity<List<MemberLanguage>> removeLanguage(@RequestBody List<String> languages){
        return  ResponseEntity.ok(jwtUserDetailsService.removeLanguage(languages));
    }

    @PostMapping("/profileImage")
    @ApiOperation(value = "upload profile image to member")
    public ResponseEntity<String> addProfileImage(@RequestPart(value = "file") MultipartFile file){
        String imageUrl =  amazonClient.uploadFile(file);
        int memberId = jwtUserDetailsService.getUserId();
        jwtUserDetailsService.saveProfileImage(imageUrl, memberId);
        return ResponseEntity.ok(imageUrl);
    }

}
