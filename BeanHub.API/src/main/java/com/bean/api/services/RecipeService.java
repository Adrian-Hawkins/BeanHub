package com.bean.api.services;

import com.bean.api.entities.Recipe;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.TypedQuery;
import java.util.List;

@Service
public class RecipeService {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public void saveRecipe(Recipe recipe) {
        entityManager.persist(recipe);
    }

    @Transactional
    public void updateRecipe(Recipe recipe) {
        entityManager.merge(recipe);
    }

    @Transactional
    public void deleteRecipe(Recipe recipe) {
        entityManager.remove(entityManager.contains(recipe) ? recipe : entityManager.merge(recipe));
    }

    @Transactional(readOnly = false)
    public Recipe getRecipeById(Integer recipeId) {
        return entityManager.find(Recipe.class, recipeId);
    }

    @Transactional(readOnly = true)
    public List<Recipe> getAllRecipes() {
        String jpql = "SELECT r FROM Recipe r";
        TypedQuery<Recipe> query = entityManager.createQuery(jpql, Recipe.class);
        return query.getResultList();
    }
}
