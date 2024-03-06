package com.bean.api.controllers;

import com.bean.api.services.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;


@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/hello")
    public String helloWorld() {
        TestService t = new TestService();
        t.someMethodToCreateUser();
        return "Hello, fdsdfsd!";
    }

    
}