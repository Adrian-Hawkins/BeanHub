package com.bean.api.services;

import com.bean.api.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
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

    @Transactional(readOnly = true)
    public User getUserByUsername(String username) {
        String jpql = "SELECT u FROM User u WHERE u.username = :username";
        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
        query.setParameter("username", username);
        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}