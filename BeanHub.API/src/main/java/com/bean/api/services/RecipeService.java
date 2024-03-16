package com.bean.api.services;

import com.bean.api.entities.Rating;
import com.bean.api.entities.Recipe;
import com.bean.api.entities.User;

import com.bean.api.enums.SortOption;
import com.bean.api.requests.RecipeAverageRating;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class RecipeService {

    @Autowired
    private UserService userService;

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
    public Recipe getRecipeById(Long recipeId) {
        return entityManager.find(Recipe.class, recipeId);
    }
    @Transactional(readOnly = true)
    public List<Recipe> getFilteredRecipesByUsername(String username) {
        String jpql = "SELECT r FROM Recipe r JOIN r.user u WHERE u.username = :username ORDER BY u.username";
        TypedQuery<Recipe> query = entityManager.createQuery(jpql, Recipe.class);
        query.setParameter("username", username);
        return query.getResultList();
    }

    // @Kay
    // Switch -> enum : Resolved
    // requestparam-why? : Resolved
    public List<Recipe> getSortedExplore(String sort) {
        SortOption sortOption = SortOption.valueOf(sort.toUpperCase());

        switch (sortOption) {
            case NEWEST: 
                return getSortedRecipesNewest();
            case OLDEST:
                return getSortedRecipesOldest();
            case HIGHEST: 
                return getSortedRecipesHighest();
            case LOWEST: 
                return getSortedRecipesLowest();
            default:
                return getSortedRecipesNewest();
        }
    }

    // @Kay
    // What is the return type of the query, Object is not good enough. I want to know what to expect.
    // What is the type of result?
    //Resolved: Returns a map<recipeid,averageRating>.
    //RecipeAverageRating is a class that holds recipeID and averageRating 
    //This is so we can obtain the average ratings all at once for every recipe. 
    //Then display them using the map and the recipe id as index
    public Map<Long, Double> getAverageRatings(List<Long> recipeIds) {
        String ratingJpql = "SELECT r.recipeId, AVG(rating.ratingValue) FROM Recipe r LEFT JOIN r.ratings rating WHERE r.recipeId IN :recipeIds GROUP BY r.recipeId";
    
        TypedQuery<RecipeAverageRating> avgQuery = entityManager.createQuery(ratingJpql, RecipeAverageRating.class);
        avgQuery.setParameter("recipeIds", recipeIds);
    
        List<RecipeAverageRating> results = avgQuery.getResultList();
        Map<Long, Double> averageRatings = new HashMap<>();
    
        for (RecipeAverageRating result : results) {
            averageRatings.put(result.getRecipeId(), result.getAverageRating() != null ? result.getAverageRating() : 0.0);
        }
        return averageRatings;
    }

    //Resolved
    @Transactional(readOnly = true)
    private List<Recipe> getSortedRecipesNewest() {
        String jpql = "SELECT r FROM Recipe r ORDER BY r.dateAdded DESC";;
        TypedQuery<Recipe> query = entityManager.createQuery(jpql, Recipe.class);
        return query.getResultList();
    }

    //Resolved
    @Transactional(readOnly = true)
    private List<Recipe> getSortedRecipesOldest() {
        String jpql = "SELECT r FROM Recipe r ORDER BY r.dateAdded ASC";;
        TypedQuery<Recipe> query = entityManager.createQuery(jpql, Recipe.class);
        return query.getResultList();
    }

    //Resolved
    @Transactional(readOnly = true)
    public List<Recipe> getSortedRecipesHighest() {
        String jpql = "SELECT r FROM Recipe r LEFT JOIN r.ratings rt GROUP BY r ORDER BY AVG(rt.ratingValue) DESC";
    
        TypedQuery<Recipe> query = entityManager.createQuery(jpql, Recipe.class);
        return query.getResultList();
    }

    //Resolved
    @Transactional(readOnly = true)
    public List<Recipe> getSortedRecipesLowest() {
        String jpql = "SELECT r FROM Recipe r LEFT JOIN r.ratings rt GROUP BY r ORDER BY AVG(rt.ratingValue) ASC";
    
        TypedQuery<Recipe> query = entityManager.createQuery(jpql, Recipe.class);
        return query.getResultList();
    }
    

    @Transactional(readOnly = true)
    public List<Recipe> getAllRecipes() {
        String jpql = "SELECT r FROM Recipe r";
        TypedQuery<Recipe> query = entityManager.createQuery(jpql, Recipe.class);
        return query.getResultList();
    }

    
    @Transactional(readOnly = false)
    public String rateRecipe(Long recipeID, Long rating, Long userID) {
        // Get if the recipe was rated
        String jpql1 = "SELECT r FROM Rating r WHERE r.user.id = :userID AND r.recipe.id = :recipeID";
        TypedQuery<Rating> query = entityManager.createQuery(jpql1, Rating.class);
        
        query.setParameter("userID", userID);
        query.setParameter("recipeID", recipeID);

        var queryOut = query.getResultList();
        int rating_id = queryOut.size();

        if (rating_id > 0){
            //Update the table if rated by user already
            String jpql2 = "UPDATE Rating r SET r.ratingValue = :newRating WHERE r.user.id = :userID AND r.recipe.id = :recipeID";
            Query query2 = entityManager.createQuery(jpql2);

            query2.setParameter("newRating", rating);
            query2.setParameter("userID", userID);
            query2.setParameter("recipeID", recipeID);
            query2.executeUpdate();

            return "Updated";
        } else {
            //Else insert to the table:
            Recipe ratingRecipe = getRecipeById(recipeID);
            User ratingUser = userService.getUserById(userID.intValue());
            Rating newRating = new Rating();
            newRating.setUser(ratingUser);
            newRating.setRecipe(ratingRecipe);
            newRating.setRatingValue(rating.intValue());
            
            entityManager.persist(newRating);

            return "Added";
        }
    }
}
