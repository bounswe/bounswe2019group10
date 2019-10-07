package com.example.backend.config;

import com.example.backend.repository.MemberRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableJpaRepositories(basePackageClasses = MemberRepository.class)
@Configuration
public class SecureAuthenticationConfiguration extends  WebSecurityConfigurerAdapter{




}
