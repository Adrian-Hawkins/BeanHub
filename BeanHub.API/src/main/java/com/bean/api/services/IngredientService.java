package com.bean.api.services;

import com.bean.api.entities.Ingredient;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.TypedQuery;
import java.util.List;

@Service
public class IngredientService {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public void saveIngredient(Ingredient ingredient) {
        entityManager.persist(ingredient);
    }

    @Transactional
    public void updateIngredient(Ingredient ingredient) {
        entityManager.merge(ingredient);
    }

    @Transactional
    public void deleteIngredient(Ingredient ingredient) {
        entityManager.remove(entityManager.contains(ingredient) ? ingredient : entityManager.merge(ingredient));
    }

    @Transactional(readOnly = false)
    public Ingredient getIngredientById(Integer ingredientId) {
        return entityManager.find(Ingredient.class, ingredientId);
    }

    @Transactional(readOnly = true)
    public List<Ingredient> getAllIngredients() {
        String jpql = "SELECT i FROM Ingredient i";
        TypedQuery<Ingredient> query = entityManager.createQuery(jpql, Ingredient.class);
        return query.getResultList();
    }
}
