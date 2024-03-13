package com.bean.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bean.api.entities.Recipe;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
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

    @Transactional(readOnly = true)
    public Long getNumRecipeRatings(int recipeID) {
        String jpql = "SELECT COUNT(r) FROM Rating r WHERE r.recipe.recipeId = :recipeID";
        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
        query.setParameter("recipeID", recipeID);
        return query.getSingleResult();
    }

    @Transactional(readOnly = false)
    public void deleteRecipe(int recipeID){
        String jpql_ri = "DELETE FROM RecipeIngredients r WHERE r.recipe.recipeId = :recipeID";
        Query deleteRecipeQuery_ri = entityManager.createQuery(jpql_ri);
        deleteRecipeQuery_ri.setParameter("recipeID", recipeID);
        deleteRecipeQuery_ri.executeUpdate();

        String jpql_r = "DELETE FROM Recipe r WHERE r.recipeId = :recipeID";
        Query deleteRecipeQuery_r = entityManager.createQuery(jpql_r);
        deleteRecipeQuery_r.setParameter("recipeID", recipeID);
        deleteRecipeQuery_r.executeUpdate();
    }
}
