package com.bean.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bean.api.entities.Recipe;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Service
public class ViewPastRecipesService {

    @Autowired
    private EntityManager entityManager;
    

    @Transactional(readOnly = true)
    public List<Recipe> getUserRecipes(int userId) {
        String jpql = "SELECT r FROM Recipe r WHERE r.user.userId = :userId";
        TypedQuery<Recipe> query = entityManager.createQuery(jpql, Recipe.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
}
