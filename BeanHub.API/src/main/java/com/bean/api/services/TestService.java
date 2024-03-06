package com.bean.api.services;

import com.bean.api.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
@Service
public class TestService {

    @Autowired
    private UserService userService = new UserService();

    public void someMethodToCreateUser() {
        User user = new User();
        user.setUsername("example_user"); // Set the username

        userService.createUser(user);
    }
}