package com.bean.api.controllers;

import com.bean.api.classes.Person;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @PostMapping("/login")
    public String login(@RequestBody Person person) {
        System.out.println(person.getName());
        return "Hello, fdsdfsd!";
    }
}
