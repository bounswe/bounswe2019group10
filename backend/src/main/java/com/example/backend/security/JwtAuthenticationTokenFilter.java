package com.example.backend.security;

import com.example.backend.domain.Member;
import com.example.backend.service.UserDetailsServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationTokenFilter  extends OncePerRequestFilter {

    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;
    @Autowired
    private UserDetailsServiceImp userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = getToken(httpServletRequest);
        if(token != null && jwtAuthenticationProvider.validateToken(token)){
            String username = jwtAuthenticationProvider.getUsernameFromToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if(userDetails != null){
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }

    public String getToken(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        if ( authHeader != null && authHeader.startsWith( "Bearer " ) ) {
            return authHeader.substring(7);
        }
        return null;
    }
}
