package com.bean.api.services;

import com.bean.api.entities.User;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public void saveUser(User user) {
        entityManager.persist(user);
    }
}
