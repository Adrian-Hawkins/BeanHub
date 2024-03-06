package com.bean.api.controllers;

import com.bean.api.entities.User;
import com.bean.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;


@RestController
@RequestMapping("/api")
public class HelloController {

    @Autowired
    private UserService userService;


    @GetMapping("/hello")
    public String helloWorld() {
        User u = userService.getUserByUsername("test");
//        System.out.println(u.getUsername());
//        u.setUsername("test");
//        userService.saveUser(u);
        return u.getUsername();
    }

    
}