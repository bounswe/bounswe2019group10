package com.example.backend.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/admin")
public class AdminController {

    @RequestMapping("/admin")
    public String admin(){
        return "admin";
    }
}
