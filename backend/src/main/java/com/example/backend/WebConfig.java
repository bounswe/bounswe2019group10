package com.example.backend;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/*")
                .allowedMethods("GET", "OPTIONS", "HEAD", "PUT", "POST", "DELETE")
                .allowedOrigins("*").allowedHeaders("*").exposedHeaders("Location", "Access-Control-Allow-Origin");
    }

}
