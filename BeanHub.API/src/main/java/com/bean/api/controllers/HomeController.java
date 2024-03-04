package com.bean.api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "Welcome to BeanHub!";
    }

    @GetMapping("/secured")
    public String secured() {
        return "Welcome to the secured area!";
    }
}
