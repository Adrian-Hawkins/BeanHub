package com.bean.api.controllers;

import com.bean.api.entities.User;
import com.bean.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
//import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Map<String, Object> registerUser(@RequestBody User user) {
        User result = userService.getUserByUsername(user.getUsername());
        Map<String, Object> ret = new HashMap<>();
        if(result == null) {
            userService.saveUser(user);
            ret.put("Status", "Success");
            ret.put("New", true);
            return ret;
        }
        ret.put("Status", "Success");
        ret.put("New", false);
        return ret;
    }
}
