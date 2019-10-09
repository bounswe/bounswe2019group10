package com.example.backend.security;

import com.example.backend.domain.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JwtAuthenticationProvider {
    @Value("${secret-key}")
    private String secret_key ;
    @Value("${expiration-length}")
    private long exDuration ;

    public String generateToken(Member member){
        Claims claims = Jwts.claims().setSubject(member.getName());
        claims.put("role", member.getRole());
        Date curDate = new Date();
        Date expirationDate = new Date(curDate.getTime() + exDuration);
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret_key)
                .setIssuedAt(curDate)
                .setExpiration(expirationDate)
                .compact();
    }

    public String getUsernameFromToken(String token){
        Claims body = Jwts.parser()
                .setSigningKey(secret_key)
                .parseClaimsJws(token)
                .getBody();
        return body.getSubject();
    }


    public String getRoleFromToken(String token){
        Claims body = Jwts.parser()
                .setSigningKey(secret_key)
                .parseClaimsJws(token)
                .getBody();
        return (String) body.get("role");
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret_key).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

    }



}
