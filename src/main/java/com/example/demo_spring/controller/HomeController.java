package com.example.demo_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // "/" <- 기본 8080 도메인 접근
    @GetMapping("/")
    public String home() {
        return "home";  // home.html
    }
}
