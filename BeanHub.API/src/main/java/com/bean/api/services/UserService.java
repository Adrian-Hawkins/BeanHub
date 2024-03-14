package com.bean.api.services;

import com.bean.api.entities.Recipe;
import com.bean.api.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public void saveUser(User user) {
        entityManager.persist(user);
    }

    @Transactional
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Transactional
    public void deleteUser(User user) {
        entityManager.remove(entityManager.contains(user) ? user : entityManager.merge(user));
    }

    @Transactional(readOnly = false)
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

    @Transactional(readOnly = false)
    public User getUserById(Integer userId) {
        return entityManager.find(User.class, userId);
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        String jpql = "SELECT u FROM User u";
        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
        return query.getResultList();
    }
}
