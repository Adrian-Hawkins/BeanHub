package com.bean.api.services;

import com.bean.api.entities.RecipeIngredients;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class RecipeIngredientsService {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public void saveRecipeIngredient(RecipeIngredients recipeIngredient) {
        entityManager.persist(recipeIngredient);
    }

    @Transactional
    public void updateRecipeIngredient(RecipeIngredients recipeIngredient) {
        entityManager.merge(recipeIngredient);
    }

    @Transactional
    public void deleteRecipeIngredient(RecipeIngredients recipeIngredient) {
        entityManager.remove(entityManager.contains(recipeIngredient) ? recipeIngredient : entityManager.merge(recipeIngredient));
    }

    @Transactional(readOnly = true)
    public RecipeIngredients getRecipeIngredientById(Integer recipeIngredientsId) {
        return entityManager.find(RecipeIngredients.class, recipeIngredientsId);
    }

    @Transactional(readOnly = true)
    public List<RecipeIngredients> getAllRecipeIngredients() {
        String jpql = "SELECT r FROM RecipeIngredients r";
        TypedQuery<RecipeIngredients> query = entityManager.createQuery(jpql, RecipeIngredients.class);
        return query.getResultList();
    }
}
