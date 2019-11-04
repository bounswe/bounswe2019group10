package com.example.backend.controller.member;

import com.example.backend.Util.JwtUserDetailsServiceUtil;
import com.example.backend.config.JwtTokenUtil;
import com.example.backend.model.member.JwtRequest;
import com.example.backend.model.member.JwtResponse;
import com.example.backend.model.member.MemberDTO;
import com.example.backend.service.member.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    private final String mailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$";

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        String username = authenticationRequest.getUsername();
                //Check if the input is of form email.

        Pattern pattern = Pattern.compile(mailRegex);

        if(pattern.matcher(username).matches()) { //The credential is of form email
            username = userDetailsService.getByMail(username).getUsername();
        }

        authenticate(username, authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(username);

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody MemberDTO user) throws Exception {
        //Handle registration attempt with same username/password
        if (userDetailsService.getByMail(user.getMail()) != null ||
                userDetailsService.getByUsername(user.getUsername()) != null) {
            throw new Exception("User already exists");
        }
        JwtUserDetailsServiceUtil serviceOutput = userDetailsService.save(user);
        if(!serviceOutput.isValid()){ //If the request is invalid return the error message
            return  ResponseEntity.ok(serviceOutput.getInfo());
        }
        return createAuthenticationToken(new JwtRequest(user.getUsername(), user.getPassword()));
    }
}
